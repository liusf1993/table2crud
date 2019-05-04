package com.hqjl.table2crud.generator

import com.hqjl.table2crud.constant.{GenerateType, ImportMapHolder}
import com.hqjl.table2crud.domain.Column
import com.hqjl.table2crud.util.{NameUtil, PathHolder}

case class GenerateFileModel(tableName: String,
                             columns: Array[Column],
                             generateType: GenerateType,
                             queryFields: Array[Array[String]]
                            ) {


  def getEntityName: String = NameUtil.formatName(this.tableName)

  def getClassName: String = NameUtil.name(this.tableName, generateType)

  def getGenerateType: GenerateType = this.generateType

  def getColumns: Array[Column] = columns

  def getPrimaryColumn: Option[Column] = columns.find(_.getPrimaryKey)

  def getPkg: String = PathHolder.pkg(generateType)

  def getImports: Array[String] = {
    getDecencyTypes.map(x => PathHolder.importByTable(x, tableName)) ++ getImportsByColumns ++ Array("java.io.Serializable", "java.util.List")
  }

  def getDecencyTypes: Array[GenerateType] = this.generateType.getDecencies

  private def getImportsByColumns: Array[String] = {
    columns.flatMap(x => ImportMapHolder.getImport(x.getType)).distinct
  }
}
