package com.hqjl.table2crud.domain

import com.hqjl.table2crud.constant.GenerateType

/**
  *
  */
object TemplateConfig {
  val TEMPLATE_SUFFIX = ".vm"
  var TEMPLATE_DIR = "templates/"

  def getTemplate(code: Code, generateType: GenerateType): String = {
    TEMPLATE_DIR + code.getTplPath + generateType.getName.toLowerCase + TEMPLATE_SUFFIX
  }
}