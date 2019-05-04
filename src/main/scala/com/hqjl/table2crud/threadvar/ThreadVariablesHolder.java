package com.hqjl.table2crud.threadvar;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

public class ThreadVariablesHolder {

  private static ThreadLocal<String> divisionFieldsLocal = new ThreadLocal<>();
  private static ThreadLocal<String> sliceFieldLocal = new ThreadLocal<>();
  private static ThreadLocal<List<Column>> columnsLocal = new ThreadLocal<>();
  private static ThreadLocal<Boolean> useCollection = new ThreadLocal<>();

  public static String getDivisionFields() {
    return divisionFieldsLocal.get();
  }

  public static void setDivisionFields(String divisionFields) {
    divisionFieldsLocal.set(divisionFields);
  }

  public static String getSliceFields() {
    return sliceFieldLocal.get();
  }

  public static void setSliceFields(String sliceFields) {
    sliceFieldLocal.set(sliceFields);
  }

  public static List<Column> getColumns() {
    return columnsLocal.get();
  }

  public static void setColumns(List<Column> divisionFields) {
    columnsLocal.set(divisionFields);
  }

  public static Boolean isUseCollection() {
    return useCollection.get();
  }

  public static void setUseCollection(Boolean divisionFields) {
    useCollection.set(divisionFields);
  }
}
