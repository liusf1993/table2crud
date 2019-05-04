package com.hqjl.table2crud.util;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.storage.Env;
import java.util.ArrayList;
import java.util.List;

/**
 * 路径工具类
 */
public class PathHolder {

  public static String path;
  public static Code code;

  public static String pkg;
  public static String javaPath;
  public static String resourcesPath;

  private static List<String> webTailList = new ArrayList<String>();

  static {
    webTailList.add("com");
    webTailList.add("net");
    webTailList.add("org");
    webTailList.add("cn");
    webTailList.add("edu");
  }

  public static void init(String path, Code code) {
    PathHolder.path = path.replaceAll("\\" + Env.sp, "/");
    PathHolder.code = code;
    analyJavaPath(path);
  }

  private static void analyJavaPath(String path) {
    String sign = code.getSign();
    String signTest = code.getSignTest();
    int index = path.indexOf(sign);
    //表示src/main/xxx标记存在
    if (index > -1) {
      PathHolder.javaPath = path.substring(0, index + sign.length());
      PathHolder.pkg = pkg(path.substring(index + sign.length()));
      PathHolder.resourcesPath = path.substring(0, index) + code.getResources() + "/";
      return;
    } else {
      index = path.indexOf(signTest);
      if (index > -1) {
        PathHolder.javaPath = path.substring(0, index + signTest.length());
        PathHolder.pkg = pkg(path.substring(index + signTest.length()));
        PathHolder.resourcesPath = path.substring(0, index) + code.getResources() + "/";
        return;
      }
    }
    //不存在的情况先要去掉project目录部分
    String projectPath = "";
    try {
      projectPath = Env.project.getBasePath();
    } catch (Exception e) {
    }
    String tmpPath = path;
    if (projectPath != null && path.startsWith(projectPath)) {
      tmpPath = path.substring(projectPath.length());
    }
    // 查找 /java/ 或者 /scala/
    String c = code.getName().toLowerCase();
    index = tmpPath.lastIndexOf("/" + c + "/");
    if (index > -1) {
      PathHolder.javaPath = projectPath + tmpPath.substring(0, index + c.length() + 2);
      PathHolder.pkg = pkg(tmpPath.substring(index + c.length() + 2));
      PathHolder.resourcesPath = projectPath + tmpPath.substring(0, index) + "/resources/";
      return;
    }

    for (String tail : webTailList) {
      index = tmpPath.lastIndexOf("/" + tail + "/");
      if (index > -1) {
        PathHolder.javaPath = projectPath + tmpPath.substring(0, index + 1);
        PathHolder.pkg = pkg(tmpPath.substring(index + 1));
        String javaPathNoTail = PathHolder.javaPath;
        if (javaPathNoTail.endsWith("/")) {
          javaPathNoTail = javaPathNoTail.substring(0, javaPathNoTail.length() - 1);
        }
        int l = javaPathNoTail.lastIndexOf("/");
        PathHolder.resourcesPath = javaPathNoTail.substring(0, l) + "/resources/";
        return;
      }
    }

    PathHolder.javaPath = path;
    PathHolder.pkg = "com.sample.biz";
    int last = PathHolder.javaPath.lastIndexOf("/");
    PathHolder.resourcesPath = projectPath + path.substring(0, last) + "/resources/";
  }

  public static String pkg(String path) {
    if (path.startsWith("/")) {
      path = path.substring(1);
    }
    if (path.endsWith("/")) {
      path = path.substring(0, path.length() - 1);
    }
    return path.replaceAll("\\/", ".");
  }

  private static String local(String path) {
    return path.replaceAll("\\/", "\\" + Env.sp);
  }

  public static String pkg(GenerateType generateType) {
    return PathHolder.pkg + "." + generateType.getPkg();
  }

  public static String impt(GenerateType generateType, String className) {
    return pkg(generateType) + "." + className;
  }

  public static String importByTable(GenerateType generateType, String tableName) {
    return pkg(generateType) + "." + NameUtil.name(tableName, generateType);
  }

}
