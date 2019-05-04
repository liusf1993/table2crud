package com.hqjl.table2crud.constant;

/**
 *
 */
public enum PathType {
  /**
   *
   */
  SRC("src"), RESOURCES("resources");
  private String path;

  PathType(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }

}