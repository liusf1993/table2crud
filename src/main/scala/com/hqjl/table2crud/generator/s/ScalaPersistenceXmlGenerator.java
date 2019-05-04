package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaPersistenceXmlGenerator;


/**
 *
 */
public class ScalaPersistenceXmlGenerator extends JavaPersistenceXmlGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
