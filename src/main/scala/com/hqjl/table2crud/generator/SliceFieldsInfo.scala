package com.hqjl.table2crud.generator

case class SliceFieldsInfo(divisionField: String, typeFields: String, conditionName: String, sqlFields: String = "", sqlCondition: String = "") {
  def getDivisionField: String = divisionField

  def getTypeFields: String = typeFields

  def getConditionName: String = conditionName

  def getSqlFields: String = sqlFields

  def getSqlCondition: String = sqlCondition
}