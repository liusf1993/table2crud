package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.ManagerImplGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.NameUtil;
import com.hqjl.table2crud.util.PathHolder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaManagerImplGenerator extends BaseGenerator implements ManagerImplGenerator {

  @Override
  public String generate(String doClassName, String boClassName, String queryClassName, String transferClassName, String daoClassName,
      String managerClassName, String managerImplClassName, Column primaryKeyColumn) {
    Setting setting = SettingManager.get();
    List<String> importList = new ArrayList<>(getImportList(primaryKeyColumn, false, true));
    importList.add(PathHolder.impt(GenerateType.Manager, managerClassName));
    importList.add(PathHolder.impt(GenerateType.DAO, daoClassName));

    Map<String, Object> map = initMap();
    map.put("className", managerImplClassName);
    map.put("objClassName", doClassName);
    map.put("boClassName", boClassName);
    map.put("managerClassName", managerClassName);
    map.put("managerPropertyName", NameUtil.lowFirst(managerClassName));
    map.put("managerUseBO", setting.isManagerUseBO());
    if (setting.isManagerUseBO()) {
      map.put("objClassName", boClassName);
      map.put("objPropertyName", NameUtil.lowFirst(boClassName));
      importList.add(PathHolder.impt(GenerateType.BO, boClassName));
      importList.add(PathHolder.impt(GenerateType.Transfer, transferClassName));
    } else {
      map.put("objClassName", doClassName);
      map.put("objPropertyName", NameUtil.lowFirst(doClassName));
      importList.add(PathHolder.impt(GenerateType.PO, doClassName));
    }
    map.put("queryClassName", queryClassName);
    map.put("queryPropertyName", NameUtil.lowFirst(queryClassName));
    map.put("transferClassName", transferClassName);
    map.put("daoClassName", daoClassName);
    map.put("daoPropertyName", NameUtil.lowFirst(daoClassName));
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
    return GenerateType.ManagerImpl;
  }
}
