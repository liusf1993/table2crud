package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.constant.GenerateType;

/**
 *
 */
public class ScalaBoGenerator extends ScalaDoGenerator {

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.BO;
  }
}
