package com.hqjl.table2crud.util;

import com.hqjl.table2crud.storage.Env;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import java.io.File;

/**
 *
 */
public class FileOpener {

  public static void openFile(File f) {
    try {
      VirtualFileManager.getInstance().syncRefresh();
      VirtualFile vf = LocalFileSystem.getInstance().findFileByIoFile(f);
      if (vf != null) {
        FileEditorManager.getInstance(Env.project).openFile(vf, false);
        //todo 晚些再研究怎样格式化文件
/*        PsiManager psiInstance = PsiManager.getInstance(Env.project);
        PsiFile file = psiInstance.findFile(vf);
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(Env.project);
        codeStyleManager.reformat(file);*/
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
