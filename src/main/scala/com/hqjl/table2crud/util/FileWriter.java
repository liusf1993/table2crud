package com.hqjl.table2crud.util;

import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.storage.SettingManager;
import com.intellij.openapi.util.io.FileUtil;
import java.io.File;
import java.io.IOException;

/**
 *
 */
public class FileWriter {

  private static String msg;

  public static boolean write(String filePath, String fileNameWithExt, byte[] contentByte) {
    File p = new File(filePath);
    p.mkdirs();
    File f = new File(filePath + fileNameWithExt);
    Setting setting = SettingManager.get();
    boolean r = false;
    if (!f.exists() || setting.overwrite()) {
      try {
        if (contentByte != null && contentByte.length > 0) {
          FileUtil.writeToFile(f, contentByte);
          r = true;
        } else {
          setMsg("content empty");
        }
      } catch (IOException e) {
        setMsg("exception: " + e.getCause());
      }
    } else {
      setMsg("file exist");
    }
    FileOpener.openFile(f);
    return r;
  }

  public static String getMsg() {
    return msg;
  }

  public static void setMsg(String msg) {
    FileWriter.msg = msg;
  }

}
