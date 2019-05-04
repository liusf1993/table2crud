package com.hqjl.table2crud.storage

import com.hqjl.table2crud.storage.domain.PluginProjectConfig
import com.intellij.openapi.components._
import com.intellij.util.xmlb.XmlSerializerUtil
import org.slf4j.LoggerFactory

/**
  *
  */
@State(name = "PluginProjectConfig", storages = Array(new Storage(value = "autoCodeGeneratorConfig.xml")))
object PluginProjectConfigHolder {
  private val logger = LoggerFactory.getLogger(classOf[PluginProjectConfigHolder])

  def getPluginProjectConfig: PluginProjectConfig = {
    if (Env.project == null) return null
    try ServiceManager.getService(Env.project, classOf[PluginProjectConfigHolder]).getState
    catch {
      case e: Exception =>
        logger.error("e", e)
        null
    }
  }
}

@State(name = "PluginProjectConfig", storages = Array(new Storage(value = "autoCodeGeneratorConfig.xml")))
class PluginProjectConfigHolder extends PersistentStateComponent[PluginProjectConfig] {
  private val pluginProjectConfig = new PluginProjectConfig

  override def getState: PluginProjectConfig = pluginProjectConfig

  override def loadState(state: PluginProjectConfig): Unit = XmlSerializerUtil.copyBean(state, pluginProjectConfig)
}