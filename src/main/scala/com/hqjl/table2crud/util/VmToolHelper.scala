package com.hqjl.table2crud.util

import com.hqjl.table2crud.constant.GenerateType
import com.hqjl.table2crud.domain.Column
import com.hqjl.table2crud.generator.{DivisionFieldInfo, GenerateFileModel, SliceFieldsInfo}
import org.apache.commons.lang.StringUtils


class VmToolHelper {

}

object VmToolHelper {
  private var tableModel: GenerateFileModel = _
  private var queryFields: Array[Array[String]] = Array.empty
  private var sliceFields: Array[String] = Array.empty

  def lowerFirstLetter(s: String): String = {
    if (StringUtils.isEmpty(s)) {
      ""
    } else {
      s.substring(0, 1).toLowerCase + s.substring(1)
    }
  }

  def upperFirstLetter(s: String): String = {
    s.capitalize
  }

  def findClassName(entityClass: String, classType: String): String = {

    val generateType = GenerateType.valueOf(classType)
    NameUtil.name(entityClass, generateType)
  }

  def findClassName(classType: String): String = {
    val entityClass = tableModel.getEntityName
    val generateType = GenerateType.valueOf(classType)
    NameUtil.name(entityClass, generateType)
  }

  def setTableModel(tableModel: GenerateFileModel): Unit = {
    this.tableModel = tableModel
  }

  def getPrimaryKeyType: String = {
    tableModel.columns.find(_.getPrimaryKey).map(x => {
      val jType = x.getType
      if (jType.contains(".")) {
        jType.substring(jType.lastIndexOf(".") + 1)
      }
      else {
        if (jType.substring(0, 1) == jType.substring(0, 1).toLowerCase) {
          if (jType == "int") {
            return "Integer"
          } else {
            jType.capitalize
          }

        } else {
          jType
        }

      }
    }).getOrElse("Long")
  }

  def setQueryFields(queryFields: Array[Array[String]]): Unit = {
    this.queryFields = queryFields
  }

  def getDivisionFieldInfos: Array[DivisionFieldInfo] = {
    val divisionFieldInfos = getQuerys.map(queryFields => {
      val queryColumns = tableModel.getColumns.filter(x => queryFields.contains(x.getProperty)).sortBy(x =>
        queryFields.indexOf(x.getProperty)).map(x => x.getProperty -> x.getType).toMap

      val typeFields = queryFields.map(x =>
        if (!queryColumns.contains(x)) throw new RuntimeException(s"field[$x] doesn't exist in columns") else Array(queryColumns(x), x))
      val ONE_BLANK = " "
      val typeFieldsStr = typeFields.map(_.mkString(" ")).mkString(",")
      val conditionName = queryFields.map(_.capitalize).mkString("And")
      val sqlFields = queryFields.map(x => s" $x=:$x").mkString(" and ")
      val sqlCondition = typeFields.map(x => s"@Param(${'"'}${x(1)}${'"'}) ${x.mkString(ONE_BLANK)}").mkString(", ")
      DivisionFieldInfo(queryFields.mkString(","), typeFieldsStr, conditionName, sqlFields, sqlCondition)
    })
    divisionFieldInfos
  }

  def getQuerys: Array[Array[String]] = this.queryFields

  def getSliceFieldInfos: Array[SliceFieldsInfo] = {
    val sliceFields = getSliceFields
    if (sliceFields.isEmpty) {
      return Array.empty[SliceFieldsInfo]
    }
    val sliceColumns = tableModel.getColumns.filter(x => sliceFields.contains(x.getProperty)).sortBy(x =>
      sliceFields.indexOf(x.getProperty)).map(x => x.getProperty -> x.getType).toMap

    val typeFields = sliceFields.map(x =>
      if (!sliceColumns.contains(x)) throw new RuntimeException(s"field[$x] doesn't exist in columns") else Array(sliceColumns(x), x))
    val ONE_BLANK = " "
    val typeFieldsStr = typeFields.map(_.mkString(" ")).mkString(",")
    val conditionName = sliceFields.map(_.capitalize).mkString("And")
    val sqlFields = sliceFields.map(x => s" $x=:$x").mkString(" and ")
    val sqlCondition = typeFields.map(x => s"@Param(${'"'}${x(1)}${'"'}) ${x.mkString(ONE_BLANK)}").mkString(", ")
    Array(SliceFieldsInfo(sliceFields.mkString(","), typeFieldsStr, conditionName, sqlFields, sqlCondition))
  }

  def getSliceFields: Array[String] = this.sliceFields

  def getSliceAndPris: Array[Column] = {
    val sliceOrPris = tableModel.getColumns.filter(x => sliceFields.contains(x.getProperty) || x.getPrimaryKey)
    sliceOrPris
  }

  def setSliceFields(sliceFields: Array[String]): Unit = {
    this.sliceFields = sliceFields
  }

  def getTableName: String = {
    tableModel.tableName
  }

  def getPrimaryKey:Column = {
    tableModel.getColumns.find(_.getPrimaryKey).get
  }



}
