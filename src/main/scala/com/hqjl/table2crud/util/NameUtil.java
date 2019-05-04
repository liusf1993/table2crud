package com.hqjl.table2crud.util;

import com.hqjl.table2crud.constant.GenerateType;
import org.apache.commons.lang.StringUtils;

/**
 * 名称工具类
 */
public class NameUtil {

  public static String toWords(String name) {
    return name.replaceAll("_", " ");
  }

  public static String formatName(String name) {
    return upFirstAll(toWords(name)).replaceAll(" ", "");
  }

  public static String upFirstAll(String s) {
    s = s.trim().replaceAll("\\s", " ");
    String[] sa = s.split(" ");
    String r = "";
    for (String o : sa) {
      if (StringUtils.isNotBlank(o)) {
        r += upFirst(o);
      }
    }
    return r;
  }

  public static String upFirst(String o) {
    return o.replaceFirst(o.substring(0, 1), o.substring(0, 1).toUpperCase());
  }

  public static String lowFirst(String o) {
    return o.replaceFirst(o.substring(0, 1), o.substring(0, 1).toLowerCase());
  }

  public static String propertyName(String s) {
    return lowFirst(formatName(s));
  }

  public static String name(String s, GenerateType generateType) {
    return generateType.getPrefix() + formatName(s) + generateType.getSuffix();
  }

}
