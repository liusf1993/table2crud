package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;

/**
 *
 */
public interface DaoImplGenerator {

  public String generate(String doClassName, String queryClassName, String daoClassName, String daoImplClassName, Column primaryKeyColumn,
      String tableName);
}
