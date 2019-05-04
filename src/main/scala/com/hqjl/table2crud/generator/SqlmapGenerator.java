package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

/**
 *
 */
public interface SqlmapGenerator {

  public String generate(String tableName, Column primaryKeyColumn, String doClassName, String daoClassName, List<Column> columnList,
      List<Column> columnQueryList);
}
