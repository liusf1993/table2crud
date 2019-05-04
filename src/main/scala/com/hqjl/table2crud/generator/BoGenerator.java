package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

/**
 *
 */
public interface BoGenerator {

  public String generate(String boClassName, List<Column> columnList);
}
