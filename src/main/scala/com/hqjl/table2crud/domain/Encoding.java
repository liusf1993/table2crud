package com.hqjl.table2crud.domain;

/**
 *
 */
public enum Encoding {
  /**
   *
   */
  UTF8("UTF-8"), GBK("GBK");
  private String name;

  Encoding(String name) {
    this.name = name;
  }

  public static Encoding get(String name) {
    if (name == null) {
      return null;
    }
    for (Encoding e : Encoding.values()) {
      if (e.getName().equals(name)) {
        return e;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }
}
