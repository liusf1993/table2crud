package com.hqjl.table2crud.storage

import com.hqjl.table2crud.domain._

/**
  *
  */
object SettingManager {
  def get: Setting = {
    val pluginConfig = PluginConfigHolder.getPluginConfig
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    val setting = new Setting
    if (pluginConfig != null) {
      setting.setAuthor(pluginConfig.author)
      setting.setLanguage(pluginConfig.language)
      setting.setCode(pluginConfig.code)
      setting.setEncoding(pluginConfig.encoding)
      setting.setTplUseCustom(pluginConfig.tplUseCustom)
      setting.setTplPath(pluginConfig.tplPathCustom)
    }
    else {
      setting.setLanguage(Language.EN.getName)
      setting.setCode(Code.JAVA.getName)
      setting.setEncoding(Encoding.UTF8.getName)
    }
    if (pluginProjectConfig != null) {
      setting.setCode(pluginProjectConfig.code)
      setting.setEncoding(pluginProjectConfig.encoding)
      setting.setPath(pluginProjectConfig.path)
      setting.setOverwrite(pluginProjectConfig.overwrite)
    }
    if (pluginConfig.getDbSettings != null) {
      setting.setDbSettings(pluginConfig.getDbSettings)
    }
    setting
  }

  def applyApp(setting: Setting): Unit = {
    val pluginConfig = PluginConfigHolder.getPluginConfig
    pluginConfig.setAuthor(setting.getAuthor)
    pluginConfig.setLanguage(setting.getLanguage)
    pluginConfig.setEncoding(setting.getEncoding)
    pluginConfig.setCode(setting.getCode)
    pluginConfig.setTplPathCustom(setting.getTplPath)
    pluginConfig.setTplUseCustom(setting.getTplUseCustom)
    pluginConfig.setDbSettings(setting.getDbSettings)
  }

  def applyPrj(setting: Setting): Unit = {
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    if (pluginProjectConfig != null) {
      if (setting.getEncoding != null) pluginProjectConfig.encoding = setting.getEncoding
      if (setting.getCode != null) pluginProjectConfig.code = setting.getCode
      if (setting.getPath != null) pluginProjectConfig.path = setting.getPath
      pluginProjectConfig.overwrite = setting.isOverwrite
    }
  }

  def applySwitch(managerUseBO: Boolean, daoLogicDelete: Boolean, daoUseSequence: Boolean, pagerQuery: Boolean, overwrite: Boolean, generateBase: Boolean): Unit = {
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    pluginProjectConfig.overwrite = overwrite
  }

  def applyPrjPath(path: String): Unit = {
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    if (pluginProjectConfig != null) pluginProjectConfig.path = path
  }

  def applyPrjCode(code: String): Unit = {
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    if (pluginProjectConfig != null) pluginProjectConfig.code = code
  }

  def applyPrjEncoding(encoding: String): Unit = {
    val pluginProjectConfig = PluginProjectConfigHolder.getPluginProjectConfig
    if (pluginProjectConfig != null) pluginProjectConfig.encoding = encoding
  }
}