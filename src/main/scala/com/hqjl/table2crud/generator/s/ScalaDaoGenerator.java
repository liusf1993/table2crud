package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaDaoGenerator;

/**
 *
 */
public class ScalaDaoGenerator extends JavaDaoGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
