package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.SQLMapConfigXmlGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import java.util.Map;

/**
 *
 */
public class JavaSQLMapConfigXmlGenerator extends BaseGenerator implements SQLMapConfigXmlGenerator {

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.SQLMapConfigXml;
  }

  @Override
  public String generate(String tableName) {
    Map<String, Object> map = initMap();
    map.put("tableName", tableName);
    Setting setting = SettingManager.get();
    map.put("encoding", setting.getEncoding());
    return generate(map);
  }
}
