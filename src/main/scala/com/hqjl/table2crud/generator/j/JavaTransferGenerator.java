package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.TransferGenerator;
import com.hqjl.table2crud.util.PathHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaTransferGenerator extends BaseGenerator implements TransferGenerator {

  @Override
  public String generate(String doClassName, String boClassName, String transferClassName, List<Column> columnList) {
    Map<String, Object> map = initMap();
    map.put("doClassName", doClassName);
    map.put("boClassName", boClassName);
    map.put("transferClassName", transferClassName);
    map.put("columnList", columnList);
    List<String> importList = getImportList(new ArrayList<Column>(), false, true);
    importList.add(PathHolder.impt(GenerateType.PO, doClassName));
    importList.add(PathHolder.impt(GenerateType.BO, boClassName));
    map.put("importList", importList);
    return generate(map);
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.Transfer;
  }
}
