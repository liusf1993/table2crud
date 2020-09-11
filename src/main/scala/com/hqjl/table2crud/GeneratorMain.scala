package com.hqjl.table2crud

import com.hqjl.table2crud.storage.Env
import com.hqjl.table2crud.ui.GeneratorMainDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

/**
  *
  */
class GeneratorMain extends AnAction {
  override def actionPerformed(e: AnActionEvent): Unit = {
    Env.project = e.getProject
    val dialog = GeneratorMainDialog.createDialog(e)
    dialog.pack()
    dialog.setVisible(true)
  }
}