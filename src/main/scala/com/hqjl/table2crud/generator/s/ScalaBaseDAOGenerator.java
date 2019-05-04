package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaBaseDAOGenerator;

/**
 *
 */
public class ScalaBaseDAOGenerator extends JavaBaseDAOGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
