package com.hqjl.table2crud.constant

/**
  *
  */
object ImportMapHolder {
  var TYPE_IMPORT_MAP: Map[String, String] = Map(
    "Date" -> "java.util.Date",
    "BigDecimal" -> "java.math.BigDecimal")


  def getImport(propertyType: String): Option[String] = {
    TYPE_IMPORT_MAP.get(propertyType)
  }

}