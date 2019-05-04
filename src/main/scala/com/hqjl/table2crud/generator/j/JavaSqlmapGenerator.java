package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.SqlmapGenerator;
import com.hqjl.table2crud.storage.Env;
import com.hqjl.table2crud.storage.SettingManager;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaSqlmapGenerator extends BaseGenerator implements SqlmapGenerator {

  @Override
  public String generate(String tableName, Column primaryKeyColumn, String doClassName, String daoClassName, List<Column> columnList,
      List<Column> columnQueryList) {
    Map<String, Object> map = initMap();
    map.put("tableName", tableName);
    map.put("primaryKeyColumn", primaryKeyColumn);
    map.put("doClassName", doClassName);
    map.put("daoClassName", daoClassName);
    map.put("haveGmtModified", haveKey(columnList, "GMT_MODIFIED"));
    map.put("haveIsDeleted", haveKey(columnList, "IS_DELETED"));
    map.put("haveDeleted", haveKey(columnList, "DELETED"));
    map.put("deleteKey", deleteKey(columnList));
    map.put("columnList", columnList);
    map.put("columnQueryList", columnQueryList);
    map.put("encoding", Env.encodeTo.name());
    Setting setting = SettingManager.get();
    map.put("daoLogDelete", setting.isDaoLogicDelete());
    map.put("daoUseSequence", setting.isDaoUseSequence());
    map.put("pagerQuery", setting.isPagerQuery());
    return generate(map);
  }

  private boolean haveKey(List<Column> columnList, String name) {
    if (columnList != null && columnList.size() > 0) {
      for (Column c : columnList) {
        if (c.getName().equals(name)) {
          return true;
        }
      }
    }
    return false;
  }

  private String deleteKey(List<Column> columnList) {

    return "{DELETE_KEY}";
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.SQLMap;
  }
}
