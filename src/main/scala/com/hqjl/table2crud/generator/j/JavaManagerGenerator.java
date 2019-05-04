package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.ManagerGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.NameUtil;
import com.hqjl.table2crud.util.PathHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaManagerGenerator extends BaseGenerator implements ManagerGenerator {


  @Override
  public String generate(String objClassName, String queryClassName, String managerClassName, Column primaryKeyColumn) {
    Setting setting = SettingManager.get();
    List<String> importList = new ArrayList<>(getImportList(primaryKeyColumn, false, true));
    //importList.add(PathHolder.impt(GenerateType.Query, queryClassName));
    if (setting.isManagerUseBO()) {
      importList.add(PathHolder.impt(GenerateType.BO, objClassName));
    } else {
      importList.add(PathHolder.impt(GenerateType.PO, objClassName));
    }
    Map<String, Object> map = initMap();
    map.put("className", managerClassName);
    map.put("objClassName", objClassName);
    map.put("objPropertyName", NameUtil.lowFirst(objClassName));
    map.put("queryClassName", queryClassName);
    map.put("queryPropertyName", NameUtil.lowFirst(queryClassName));
//        map.put("primaryKeyColumn", primaryKeyColumn);
    try {
      map.put("primaryKeyName", primaryKeyColumn.getProperty());
      map.put("primaryKeyType", primaryKeyColumn.getType());
    } catch (Exception e) {
      map.put("primaryKeyName", "id");
      map.put("primaryKeyType", "Long");
    }
    map.put("importList", importList);
    return generate(map);
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.Manager;
  }
}
