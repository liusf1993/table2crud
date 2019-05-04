package com.hqjl.table2crud.util;

import java.util.HashMap;

/**
 *
 */
public class TypeChanger {

  public static HashMap<String, String> typeMap = new HashMap<String, String>();

  static {
    /**
     * mysql
     */
    typeMap.put("TINYINT", "boolean");
    typeMap.put("SMALLINT", "int");
    typeMap.put("MEDIUMINT", "int");
    typeMap.put("INT", "int");
    typeMap.put("BIGINT", "long");
    typeMap.put("DECIMAL", "BigDecimal");
    typeMap.put("FLOAT", "float");
    typeMap.put("DOUBLE", "double");
    typeMap.put("REAL", "float");
    typeMap.put("BIT", "boolean");
    typeMap.put("BOOLEAN", "boolean");
    typeMap.put("SERIAL", "long");
    typeMap.put("DATE", "Date");
    typeMap.put("DATETIME", "Date");
    typeMap.put("TIMESTAMP", "long");
    typeMap.put("TIME", "String");
    typeMap.put("YEAR", "int");
    typeMap.put("CHAR", "String");
    typeMap.put("VARCHAR", "String");
    typeMap.put("TINYTEXT", "String");
    typeMap.put("TEXT", "String");
    typeMap.put("MEDIUMTEXT", "String");
    typeMap.put("LONGTEXT", "String");
    typeMap.put("BINARY", "String");
    typeMap.put("VARBINARY", "String");
    //有待考验,blob里面一般存储字节流
    typeMap.put("TINYBLOB", "Object");
    typeMap.put("MEDIUMBLOB", "Object");
    typeMap.put("BLOB", "Object");
    typeMap.put("LONGBLOB", "Object");
    typeMap.put("ENUM", "String");
    typeMap.put("SET", "String");
    /**
     * oracle
     */
    typeMap.put("NUMBER", "long");
    typeMap.put("RAW", "String");
  }

  public static String clean(String typeStr) {
    return typeStr.replaceAll("\\(.*\\)*", "").toUpperCase();
  }

  public static String getType(String typeStr) {

    String type = typeMap.get(typeStr);
    if (type == null) {
      type = "String";
    }
    return type;
  }
}
