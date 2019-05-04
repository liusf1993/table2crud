package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;

/**
 *
 */
public interface ManagerGenerator {

  public String generate(String objClassName, String queryClassName, String managerClassName, Column primaryKeyColumn);
}
