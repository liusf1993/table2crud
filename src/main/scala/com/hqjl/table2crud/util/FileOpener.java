package com.hqjl.table2crud.util;

import com.hqjl.table2crud.storage.Env;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
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
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
