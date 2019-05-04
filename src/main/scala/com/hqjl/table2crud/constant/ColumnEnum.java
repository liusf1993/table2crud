package com.hqjl.table2crud.constant;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public enum ColumnEnum {
  /**
   *
   */
  NO("NO", 0),
  PO("PO", 1),
  Column("Column", 2),
  DBType("DBType", 3),
  CodeType("CodeType", 4),
  ClassProperty("ClassProperty", 5),
  Primary("Primary", 6),
  Comment("Comment", 7);
  private String name;
  private int order;

  ColumnEnum(String name, int order) {
    this.name = name;
    this.order = order;
  }

  public static List<String> getColumnNameList() {
    List<String> list = new ArrayList<String>();
    for (ColumnEnum c : ColumnEnum.values()) {
      list.add(c.getName());
    }
    return list;
  }

  public static String[] getColumnNames() {
    List<String> list = getColumnNameList();
    return list.toArray(new String[0]);
  }

  public static ColumnEnum get(String name) {
    if (name == null) {
      return null;
    }
    for (ColumnEnum c : ColumnEnum.values()) {
      if (name.toLowerCase().equals(c.getName().toLowerCase())) {
        return c;
      }
    }
    return null;
  }

  public static ColumnEnum getByOrder(int order) {
    for (ColumnEnum c : ColumnEnum.values()) {
      if (order == c.getOrder()) {
        return c;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public int getOrder() {
    return order;
  }
}
