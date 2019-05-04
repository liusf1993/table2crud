package com.hqjl.table2crud.storage

import com.hqjl.table2crud.storage.domain.PluginConfig
import com.intellij.openapi.components._
import com.intellij.util.xmlb.XmlSerializerUtil
import org.slf4j.LoggerFactory

/**
  *
  */
@State(name = "PluginConfig", storages = Array(new Storage(value = "autoCodeSettings.xml")))
object PluginConfigHolder {
  private val logger = LoggerFactory.getLogger(classOf[PluginConfigHolder])

  def getPluginConfig: PluginConfig = {
    if (Env.project == null) return null
    try ServiceManager.getService(Env.project, classOf[PluginConfigHolder]).getState
    catch {
      case e: Exception =>
        logger.error("e", e)
        null
    }
  }
}

@State(name = "PluginConfig", storages = Array(new Storage(value = "autoCodeSettings.xml")))
class PluginConfigHolder extends PersistentStateComponent[PluginConfig] {
  private val pluginProjectConfig = new PluginConfig

  override def getState: PluginConfig = pluginProjectConfig

  override def loadState(state: PluginConfig): Unit = XmlSerializerUtil.copyBean(state, pluginProjectConfig)
}