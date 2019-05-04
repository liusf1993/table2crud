package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;

/**
 *
 */
public class JavaQueryGenerator extends JavaDoGenerator {

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.Query;
  }
}
