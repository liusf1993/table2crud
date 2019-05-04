package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.constant.GenerateType;

/**
 *
 */
public class ScalaQueryGenerator extends ScalaDoGenerator {

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.Query;
  }
}
