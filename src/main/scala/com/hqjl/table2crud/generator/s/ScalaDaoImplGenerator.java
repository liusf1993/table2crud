package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaDaoImplGenerator;

/**
 *
 */
public class ScalaDaoImplGenerator extends JavaDaoImplGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
