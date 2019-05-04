package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.generator.BaseGenerator;

/**
 *
 */
public class JavaBaseDAOGenerator extends BaseGenerator {

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.BaseDAO;
  }
}
