package com.hqjl.table2crud.generator.s;

import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.j.JavaSQLMapConfigXmlGenerator;


/**
 *
 */
public class ScalaSQLMapConfigXmlGenerator extends JavaSQLMapConfigXmlGenerator {

  @Override
  public Code getCode() {
    return Code.SCALA;
  }

}
