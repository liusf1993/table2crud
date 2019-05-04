package com.hqjl.table2crud.util

import java.io.StringWriter
import java.util

import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.tools.{ToolContext, ToolInfo, Toolbox}

/**
  *
  */
class TemplateUtil {

}

object TemplateUtil {
  val velocityEngine = new VelocityEngine
  var encoding = "UTF-8"
  velocityEngine.setProperty("runtime.log.logsystem.log4j.logger", System.getProperty("user.dir") + "/velocity.log")
  val context = new ToolContext()
  val toolInfo = new ToolInfo("vmToolHelper", classOf[VmToolHelper])
  val key2toolInfoMap = new util.HashMap[String, ToolInfo]()
  key2toolInfoMap.put("helper", toolInfo)
  context.addToolbox(new Toolbox(key2toolInfoMap))

  def parseByVm(vmTplPath: String, contextMap: util.Map[String, AnyRef]): String = {
    val tplContent = ResourceUtil.load(vmTplPath, encoding)
    parse(tplContent, contextMap)
  }

  private def parse(tplContent: String, contextMap: util.Map[String, AnyRef]) = {


    import scala.collection.JavaConversions._
    for (k <- contextMap.keySet) {
      context.put(k, contextMap.get(k))
    }
    val sw = new StringWriter
    velocityEngine.evaluate(context, sw, "", tplContent)
    sw.toString
  }
}

