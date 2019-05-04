package com.hqjl.table2crud.storage.domain

import java.io.Serializable

/**
  *
  */
@SerialVersionUID(-4385855355672890609L)
class PluginProjectConfig extends Serializable {
  var code: String = "JAVA"
  var encoding: String = "UTF-8"
  var path: String = _
  var overwrite: Boolean = false
  var sql: String = "INPUT SQL HERE JUST LIKE BELOW  \n\n ================================================ \n\n" + "create  table example(\n" + "  id bigint auto_increment primary key not null ,\n" + "  name varchar(255) not null\n" + ")" + "\n" + "\n" + " ================================================ \n" + "\n"

  def setCode(code: String): PluginProjectConfig = {
    this.code = code
    this
  }

  def getCode: String = this.code

  def setEncoding(encoding: String): PluginProjectConfig = {
    this.encoding = encoding
    this
  }

  def getEncoding: String = this.encoding

  def setPath(path: String): PluginProjectConfig = {
    this.path = path
    this
  }

  def getPath: String = this.path

  def setOverwrite(overwrite: Boolean): PluginProjectConfig = {
    this.overwrite = overwrite
    this
  }

  def getOverwrite: Boolean = this.overwrite

  def setSql(sql: String): PluginProjectConfig = {
    this.sql = sql
    this
  }

  def getSql: String = this.sql


}