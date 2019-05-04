package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaTransferGenerator;

/**
 *
 */
public class ScalaTransferGenerator extends JavaTransferGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
