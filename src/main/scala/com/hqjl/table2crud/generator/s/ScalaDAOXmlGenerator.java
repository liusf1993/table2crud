package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaDAOXmlGenerator;


/**
 *
 */
public class ScalaDAOXmlGenerator extends JavaDAOXmlGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
