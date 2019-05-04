package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;

/**
 *
 */
public interface ManagerImplGenerator {

  public String generate(String doClassName, String boClassName, String queryClassName, String transferClassName, String daoClassName,
      String managerClassName, String managerImplClassName, Column primaryKeyColumn);
}
