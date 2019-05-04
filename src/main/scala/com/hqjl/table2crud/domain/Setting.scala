package com.hqjl.table2crud.domain

/**
  *
  */
class Setting() {
  var daoLogicDelete = false
  var managerUseBO = false
  var daoUseSequence = false
  var pagerQuery = false
  var overwrite = false
  var generateBase = false
  /**
    * //作者
    */
  private var author: String = _
  /**
    * //语言
    */
  private var language: String = _
  /**
    * //编码
    */
  private var encoding: String = _
  /**
    * 输出代码类型
    */
  private var code: String = _
  /**
    * 输出路径
    */
  private var path: String = _
  /**
    * 模板路径
    */
  private var tplPath: String = _
  /**
    * 是否使用自定义模板
    */
  private var tplUseCustom = false

  private var dbSettings: DBSettings = _

  def this(author: String, encoding: Encoding, code: Code) {
    this()
    this.author = author
    this.encoding = encoding.getName
    this.code = code.getName
  }

  def this(encoding: Encoding, code: Code, path: String) {
    this()
    this.encoding = encoding.getName
    this.code = code.getName
    this.path = path
  }

  def setSwitch(managerUseBO: Boolean, daoLogicDelete: Boolean, daoUseSequence: Boolean, pagerQuery: Boolean, overwrite: Boolean, generateBase: Boolean): Unit = {
    this.managerUseBO = managerUseBO
    this.daoLogicDelete = daoLogicDelete
    this.daoUseSequence = daoUseSequence
    this.pagerQuery = pagerQuery
    this.overwrite = overwrite
    this.generateBase = generateBase
  }

  def getAuthor: String = author

  def setAuthor(author: String): Unit = this.author = author

  def getEncoding: String = encoding

  def setEncoding(encoding: String): Unit = this.encoding = encoding

  def getCode: String = code

  def setCode(code: String): Unit = this.code = code

  def getLanguage: String = language

  def setLanguage(language: String): Unit = this.language = language

  def getPath: String = path

  def setPath(path: String): Unit = this.path = path

  def isDaoLogicDelete: Boolean = daoLogicDelete

  def setDaoLogicDelete(daoLogicDelete: Boolean): Unit = this.daoLogicDelete = daoLogicDelete

  def isManagerUseBO: Boolean = managerUseBO

  def setManagerUseBO(managerUseBO: Boolean): Unit = this.managerUseBO = managerUseBO

  def isDaoUseSequence: Boolean = daoUseSequence

  def setDaoUseSequence(daoUseSequence: Boolean): Unit = this.daoUseSequence = daoUseSequence

  def isPagerQuery: Boolean = pagerQuery

  def setPagerQuery(pagerQuery: Boolean): Unit = this.pagerQuery = pagerQuery

  def isOverwrite: Boolean = overwrite

  def setOverwrite(overwrite: Boolean): Unit = this.overwrite = overwrite

  def isGenerateBase: Boolean = generateBase

  def setGenerateBase(generateBase: Boolean): Unit = this.generateBase = generateBase

  def getTplPath: String = tplPath

  def setTplPath(tplPath: String): Unit = this.tplPath = tplPath

  def getTplUseCustom: Boolean = tplUseCustom

  def setTplUseCustom(tplUseCustom: Boolean): Unit = this.tplUseCustom = tplUseCustom

  def setDbSettings(dbSettings: DBSettings): Setting = {
    this.dbSettings = dbSettings
    this
  }

  def getDbSettings: DBSettings = this.dbSettings


}