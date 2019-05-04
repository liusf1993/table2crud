package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.DAOXmlGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.NameUtil;
import java.util.Map;

/**
 *
 */
public class JavaDAOXmlGenerator extends BaseGenerator implements DAOXmlGenerator {

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.DAOXml;
  }

  @Override
  public String generate(String daoClassName, String daoImplClassName) {
    Map<String, Object> map = initMap();
    map.put("daoClassName", daoClassName);
    map.put("daoPropertyName", NameUtil.lowFirst(daoClassName));
    map.put("daoImplClassName", daoImplClassName);
    Setting setting = SettingManager.get();
    map.put("encoding", setting.getEncoding());
    return generate(map);
  }
}
