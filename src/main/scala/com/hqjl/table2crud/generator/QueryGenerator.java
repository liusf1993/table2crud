package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

/**
 *
 */
public interface QueryGenerator {

  public String generate(String queryClassName, List<Column> columnQueryList);
}
