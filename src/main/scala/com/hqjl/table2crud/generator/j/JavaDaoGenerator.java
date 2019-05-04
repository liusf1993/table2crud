package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.DaoGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.NameUtil;
import com.hqjl.table2crud.util.PathHolder;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaDaoGenerator extends BaseGenerator implements DaoGenerator {

  @Override
  public String generate(String doClassName, String queryClassName, String daoClassName, Column primaryKeyColumn) {
    Map<String, Object> map = initMap();
    map.put("objClassName", doClassName);
    map.put("objPropertyName", NameUtil.lowFirst(doClassName));
    map.put("className", daoClassName);
    map.put("queryClassName", queryClassName);
    map.put("queryPropertyName", NameUtil.lowFirst(queryClassName));
    try {
      map.put("primaryKeyName", primaryKeyColumn.getProperty());
      String type = primaryKeyColumn.getType();
      map.put("primaryKeyType", type);
    } catch (Exception e) {
      map.put("primaryKeyName", "id");
      map.put("primaryKeyType", "Long");
    }
    List<String> importList = getImportList(primaryKeyColumn, false, true);
    importList.add(PathHolder.impt(GenerateType.PO, doClassName));

//        importList.add(PathHolder.impt(GenerateType.BaseDAO, GenerateType.BaseDAO.getName()));
    map.put("importList", importList);
    Setting setting = SettingManager.get();
    map.put("daoLogicDelete", setting.isDaoLogicDelete());
    return super.generate(map);
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.DAO;
  }
}
