package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaBaseQueryGenerator;

/**
 *
 */
public class ScalaBaseQueryGenerator extends JavaBaseQueryGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
