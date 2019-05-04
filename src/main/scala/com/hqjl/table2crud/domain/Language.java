package com.hqjl.table2crud.domain;

/**
 *
 */
public enum Language {
  /**
   *
   */
  CHS("Chinese"), EN("English");
  private String name;

  Language(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
