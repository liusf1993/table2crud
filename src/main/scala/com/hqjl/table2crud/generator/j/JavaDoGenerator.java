package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.DoGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.PathHolder;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaDoGenerator extends BaseGenerator implements DoGenerator {

  public String generate(String className, List<Column> columnList) {
    Map<String, Object> map = initMap();
    map.put("className", className);
    map.put("columnList", columnList);
    List<String> importList = getImportList(columnList, true);
    if (getGenerateType().equals(GenerateType.Query)) {
      Setting setting = SettingManager.get();
      map.put("pagerQuery", setting.isPagerQuery());
      importList.add(PathHolder.impt(GenerateType.BaseQuery, GenerateType.BaseQuery.getName()));
    } else {
      map.put("pagerQuery", false);
    }
    map.put("importList", importList);
    return super.generate(map);
  }

  public String generateWithDbName(String tableName, String className, List<Column> columnList) {
    Map<String, Object> map = initMap();
    map.put("tableName", tableName);
    map.put("className", className);
    map.put("columnList", columnList);
    List<String> importList = getImportList(columnList, true);
    if (getGenerateType().equals(GenerateType.Query)) {
      Setting setting = SettingManager.get();
      map.put("pagerQuery", setting.isPagerQuery());
      importList.add(PathHolder.impt(GenerateType.BaseQuery, GenerateType.BaseQuery.getName()));
    } else {
      map.put("pagerQuery", false);
    }
    map.put("importList", importList);
    return super.generate(map);
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.PO;
  }
}
