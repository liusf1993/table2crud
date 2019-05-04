package com.hqjl.table2crud.domain

import com.hqjl.table2crud.util.{NameUtil, TypeChanger}

/**
  *
  */
class Column() {
  private var name: String = _
  private var jType: String = _
  private var typeStr: String = _
  private var comment: String = _
  private var primaryKey: Boolean = false
  private var property: String = _
  private var dbField: String = _

  def this(name: String, typeStr: String) {
    this()
    this.name = name
    this.typeStr = TypeChanger.clean(typeStr)
    this.jType = TypeChanger.getType(this.typeStr)
  }

  def this(name: String, typeStr: String, primaryKey: Boolean) {
    this(name, typeStr)
    this.primaryKey = primaryKey
  }

  def getType: String = jType

  def setType(`type`: String): Column = {
    this.jType = `type`
    this
  }

  def getTypeStr: String = typeStr

  def setTypeStr(typeStr: String): Column = {
    this.typeStr = typeStr
    this
  }

  def setName(name: String): Column = {
    this.name = name
    this
  }

  def getComment: String = comment

  def setComment(comment: String): Column = {
    this.comment = comment
    this
  }

  def getPrimaryKey: Boolean = primaryKey

  def setPrimaryKey(isPrimaryKey: Boolean): Column = {
    this.primaryKey = isPrimaryKey
    this
  }

  def getDbField: String = dbField

  def setDbField(dbField: String): Column = {
    this.dbField = dbField
    this
  }

  def getKey: String = {
    if (property == null) return null
    NameUtil.upFirst(property)
  }

  def getPrimaryStr: String = if (this.primaryKey) "Y" else ""

  override def toString: String = {
    var r = "{"
    if (name != null) r += "\"name\":\"" + name + "\""
    if (property != null) {
      if (!(r == "{")) r += ","
      r += "\"property\":\"" + property + "\""
    }
    if (jType != null) {
      if (!(r == "{")) r += ","
      r += "\"type\":\"" + jType + "\""
    }
    if (typeStr != null) {
      if (!(r == "{")) r += ","
      r += "\"typeStr\":\"" + typeStr + "\""
    }
    if (comment != null) {
      if (!(r == "{")) r += ","
      r += "\"comment\":\"" + comment + "\""
    }
    else {
      if (!(r == "{")) r += ","
      r += "\"comment\":\"" + property + "\""
    }
    if (primaryKey) {
      if (!(r == "{")) r += ","
      r += "\"isPrimaryKey\":\"" + primaryKey + "\""
    }
    r += "}"
    r
  }

  def getProperty: String = {
    if (property == null) {
      property = NameUtil.propertyName(this.getName)
      if (property.endsWith("Id")) property = property.substring(0, property.length - 2) + "ID"
    }
    property
  }

  def getName: String = name

  def setProperty(property: String): Column = {
    this.property = property
    this
  }


}

object Column {

}


