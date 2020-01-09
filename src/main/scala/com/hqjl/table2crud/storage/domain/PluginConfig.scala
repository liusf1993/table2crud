package com.hqjl.table2crud.storage.domain

import com.hqjl.table2crud.domain.DBSettings
import org.apache.commons.lang3.StringUtils

/**
  *
  */
@SerialVersionUID(-2408231700721356773L)
class PluginConfig extends Serializable {

  private var author: String = _
  var language: String = "english"
  var code: String = "JAVA"
  var encoding: String = "UTF-8"
  var tplUseCustom: Boolean = false
  var tplPathCustom: String = ""
  var dbSettings: DBSettings = DBSettings()

  def this(author: String = System.getProperty("user.name"),
           language: String = "english",
           code: String = "JAVA",
           encoding: String = "UTF-8",
           tplUseCustom: Boolean = false,
           tplPathCustom: String = "",
           dbSettings: DBSettings) {
    this
    this.author = author
    this.language = language
    this.code = code
    this.encoding = encoding
    this.dbSettings = dbSettings

  }

  def setAuthor(author: String): PluginConfig = {
    this.author = author
    this
  }

  def getAuthor: String = {
    if (StringUtils.isBlank(author)) {
      System.getProperty("user.name")
    } else {
      this.author
    }
  }

  def setLanguage(language: String): PluginConfig = {
    this.language = language
    this
  }

  def getLanguage: String = this.language

  def setCode(code: String): PluginConfig = {
    this.code = code
    this
  }

  def getCode: String = this.code

  def setEncoding(encoding: String): PluginConfig = {
    this.encoding = encoding
    this
  }

  def getEncoding: String = this.encoding

  def setTplUseCustom(tplUseCustom: Boolean): PluginConfig = {
    this.tplUseCustom = tplUseCustom
    this
  }

  def getTplUseCustom: Boolean = this.tplUseCustom

  def setTplPathCustom(tplPathCustom: String): PluginConfig = {
    this.tplPathCustom = tplPathCustom
    this
  }

  def getTplPathCustom: String = this.tplPathCustom

  def setDbSettings(dbSettings: DBSettings): PluginConfig = {
    this.dbSettings = dbSettings
    this
  }

  def getDbSettings: DBSettings = this.dbSettings


}

object PluginConfig {
  def apply(

             author: String = System.getProperty("user.name"),
             language: String = "english",
             code: String = "JAVA",
             encoding: String = "UTF-8",
             tplUseCustom: Boolean = false,
             tplPathCustom: String = "",
             dbSettings: DBSettings = DBSettings()
           ): PluginConfig = new PluginConfig(
    author,
    language,
    code,
    encoding,
    tplUseCustom,
    tplPathCustom,
    dbSettings
  )
}