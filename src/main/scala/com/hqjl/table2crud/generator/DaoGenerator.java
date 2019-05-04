package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;

/**
 *
 */
public interface DaoGenerator {

  public String generate(String doClassName, String queryClassName, String daoClassName, Column primaryKeyColumn);
}
