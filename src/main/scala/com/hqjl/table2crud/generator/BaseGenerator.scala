package com.hqjl.table2crud.generator

import java.util
import java.util.Date

import com.hqjl.table2crud.constant.{GenerateType, ImportMapHolder}
import com.hqjl.table2crud.domain.{Code, Column, TemplateConfig}
import com.hqjl.table2crud.storage.SettingManager
import com.hqjl.table2crud.threadvar.ThreadVariablesHolder
import com.hqjl.table2crud.util.{NameUtil, PathHolder, TemplateUtil, VmToolHelper}
import org.apache.commons.httpclient.util.DateUtil

import scala.collection.mutable.ArrayBuffer

/**
  *
  */

class BaseGenerator {
  private var generateType = GenerateType.PO

  def this(generateType: GenerateType) {
    this
    this.generateType = generateType
  }


  def generate: String = {
    val map = initMap
    generate(getTemplate, map)
  }

  def generate(className: String, queryClassName: String, daoClassName: String, primaryKeyColumn: Column): String = {
    val map = initMap
    map.put("objClassName", className)
    map.put("objPropertyName", NameUtil.lowFirst(className))
    map.put("className", className)
    map.put("queryClassName", queryClassName)
    map.put("queryPropertyName", NameUtil.lowFirst(queryClassName))
    try {
      map.put("primaryKeyName", primaryKeyColumn.getProperty)
      val primaryKeyType = primaryKeyColumn.getType
      map.put("primaryKeyType", primaryKeyType)
    } catch {
      case _: Exception =>
        map.put("primaryKeyName", "id")
        map.put("primaryKeyType", "Long")
    }
    val importList = getImportList(primaryKeyColumn, objectClass = true, addList = true)
    importList.add(PathHolder.impt(GenerateType.PO, className))
    map.put("importList", importList)
    generate(map)
  }

  def generate(map: util.Map[String, AnyRef]): String = generate(getTemplate, map)

  private def getTemplate: String = TemplateConfig.getTemplate(getCode, getGenerateType)

  def generate(tpl: String, map: util.Map[String, AnyRef]): String = TemplateUtil.parseByVm(tpl, map)

  def getImportList(column: Column, objectClass: Boolean, addList: Boolean): util.List[String] = {
    val columns = new util.ArrayList[Column]
    columns.add(column)
    getImportList(columns, objectClass, addList)
  }

  def getImportList(columnList: util.List[Column], objectClass: Boolean, addList: Boolean): util.List[String] = {
    val importList = columnList.toArray(new Array[Column](0)).flatMap(x => ImportMapHolder.getImport(x.getType)) ++
      Array("java.io.Serializable", "java.util.List", "java.util.ArrayList")
    util.Arrays.asList(importList.toSeq: _*)
  }

  def getCode = Code.JAVA

  def getGenerateType: GenerateType = this.generateType

  def initMap: util.Map[String, AnyRef] = {
    val map = new util.HashMap[String, AnyRef]
    val setting = SettingManager.get
    val divisionFields = ThreadVariablesHolder.getDivisionFields.split("#").filter(_.length > 0)
    val divisionFieldInfos = divisionFields.map(divisionField => {
      val divisionFieldArray = divisionField.split(",").filter(x => x.length > 0)
      val columns = ThreadVariablesHolder.getColumns.toArray(new Array[Column](0)).filter(x => divisionFieldArray.contains(x.getProperty)).sortBy(x =>
        divisionFieldArray.indexOf(x.getProperty)).map(x => x.getProperty -> x.getType).toMap

      val typeFields = divisionFieldArray.map(x =>
        if (!columns.contains(x)) throw new RuntimeException(s"field[$x] doesn't exist in columns") else
          Array(columns(x), x))
      val ONE_BLANK = " "
      val typeFieldsStr = typeFields.map(_.mkString(" ")).mkString(",")
      val conditionName = divisionFieldArray.map(_.capitalize).mkString("And")
      val sqlFields = divisionFieldArray.map(x => s" $x=:$x").mkString(" and ")
      val sqlCondition = typeFields.map(x => s"@Param(${'"'}${x(1)}${'"'}) ${x.mkString(ONE_BLANK)}").mkString(", ")
      DivisionFieldInfo(divisionField, typeFieldsStr, conditionName, sqlFields, sqlCondition)

    })
    map.put("divisionFieldInfos", divisionFieldInfos)
    map.put("user", setting.getAuthor)
    map.put("isCollection", ThreadVariablesHolder.isUseCollection)
    map.put("today", DateUtil.formatDate(new Date, "yyyy/MM/dd HH:mm:ss"))
    map.put("package", PathHolder.pkg(getGenerateType))
    map
  }

  def generate(className: String, entityName: String, dependenciesClasses: util.List[String]): String = {
    val map = initMap
    map.put("objClassName", entityName)
    map.put("objPropertyName", NameUtil.lowFirst(className))
    map.put("className", className)
    map.put("importList", dependenciesClasses)
    generate(map)
  }

  def generateContent(generateFileModel: GenerateFileModel): String = {
    VmToolHelper.setTableModel(generateFileModel)
    val queryFields: Array[Array[String]] = ThreadVariablesHolder.getDivisionFields.split("#").filter(_.length > 0).map(_.split(","))
    val sliceFields: Array[String] = ThreadVariablesHolder.getSliceFields.split(",").filter(_.length > 0)
    VmToolHelper.setQueryFields(queryFields)
    VmToolHelper.setSliceFields(sliceFields)
    val map = initMap
    val lowerColumnNames = generateFileModel.getColumns.map(_.getProperty.toLowerCase)
    val implementClasses = new ArrayBuffer[String]()
    val overrides = new ArrayBuffer[String]()

    if (Array("entryear", "regionid").forall(lowerColumnNames.contains)) {
      implementClasses.append("com.hqjl.basic.data.interfaces.IRgnEntrYearRelated")
      overrides.append("entrYear")
      overrides.append("regionID")
    }
    else if (lowerColumnNames.contains("regionid")) {
      implementClasses.append("com.hqjl.basic.data.interfaces.IRegionRelated")
      overrides.append("regionID")
    }
    if (Array("schoolid", "gradeid").forall(lowerColumnNames.contains)) {
      implementClasses.append("com.hqjl.basic.data.interfaces.ISchGrdRelated")
      overrides.append("schoolID")
      overrides.append("gradeID")
    } else if (Array("schoolid").forall(lowerColumnNames.contains)) {
      implementClasses.append("com.hqjl.basic.data.interfaces.ISchoolRelated")
      overrides.append("schoolID")
    }
    if (Array("userid").forall(lowerColumnNames.contains)) {
      implementClasses.append("com.hqjl.basic.data.interfaces.IUserRelated")
      overrides.append("userID")
    }
    implementClasses.append("java.io.Serializable")
    val className = generateFileModel.getEntityName
    map.put("objClassName", generateFileModel.getEntityName)
    map.put("tableName", generateFileModel.tableName)
    map.put("entityName", generateFileModel.getEntityName)
    map.put("entityClass", generateFileModel.getEntityName)
    map.put("className", generateFileModel.getClassName)
    map.put("objPropertyName", NameUtil.lowFirst(className))
    map.put("importList", generateFileModel.getImports ++ implementClasses)
    map.put("implements", implementClasses.map(x => x.substring(x.lastIndexOf(".") + 1)).mkString(", "))
    map.put("columnList", generateFileModel.getColumns)
    map.put("primaryKeyType", generateFileModel.getPrimaryColumn.map(_.getType).orNull)
    map.put("overrides", util.Arrays.asList(overrides: _*))
    generate(map)
  }

  def getImportList(columnList: util.List[Column], objectClass: Boolean): util.List[String] = getImportList(columnList, objectClass, addList = false)

}