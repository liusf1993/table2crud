package com.hqjl.table2crud.storage;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.encoding.EncodingManager;
import com.intellij.openapi.vfs.encoding.EncodingProjectManager;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;

/**
 *
 */
public class Env {

  public static String sp = System.getProperty("file.separator");
  public static Project project;

  public static Charset encodeTo = StandardCharsets.UTF_8;
  public static Charset encodeFrom = StandardCharsets.UTF_8;

  @NotNull
  public static Charset getProjectCharset() {
    try {
      return EncodingProjectManager.getInstance(project).getDefaultCharset();
    } catch (Exception e) {
      return StandardCharsets.UTF_8;
    }
  }

  @NotNull
  public static Charset getIDECharset() {
    Charset ideCharset;
    try {
      ideCharset = EncodingManager.getInstance().getDefaultCharset();
    } catch (Exception e) {
      try {
        ideCharset = CharsetToolkit.getDefaultSystemCharset();
      } catch (Exception e1) {
        ideCharset = Charset.forName("UTF-8");
      }
    }
    return ideCharset;
  }

  @NotNull
  public static Charset getCharsetFromEncoding(String encoding) {
    try {
      return Charset.forName(encoding);
    } catch (Exception e) {
      return Charset.defaultCharset();
    }
  }
}
