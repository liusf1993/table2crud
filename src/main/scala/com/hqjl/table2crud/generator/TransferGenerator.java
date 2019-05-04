package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.domain.Column;
import java.util.List;

/**
 *
 */
public interface TransferGenerator {

  public String generate(String doClassName, String boClassName, String transferClassName, List<Column> columnList);
}
