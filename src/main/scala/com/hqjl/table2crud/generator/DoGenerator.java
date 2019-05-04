package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

/**
 *
 */
public interface DoGenerator {

  public String generate(String className, List<Column> columnList);
}
