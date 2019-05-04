package com.hqjl.table2crud.generator.j;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.generator.BaseGenerator;
import com.hqjl.table2crud.generator.DaoImplGenerator;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.NameUtil;
import com.hqjl.table2crud.util.PathHolder;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class JavaDaoImplGenerator extends BaseGenerator implements DaoImplGenerator {

  @Override
  public String generate(String doClassName, String queryClassName, String daoClassName, String daoImplClassName, Column primaryKeyColumn,
      String tableName) {
    Map<String, Object> map = initMap();
    map.put("className", daoImplClassName);
    map.put("doClassName", doClassName);
    map.put("queryClassName", queryClassName);
    map.put("daoClassName", daoClassName);
    map.put("daoImplClassName", daoImplClassName);
    map.put("doPropertyName", NameUtil.lowFirst(doClassName));
    map.put("queryPropertyName", NameUtil.lowFirst(queryClassName));
//        map.put("primaryKeyColumn", primaryKeyColumn);
    try {
      map.put("primaryKeyName", primaryKeyColumn.getProperty());
      map.put("primaryKeyNameAtMethod", NameUtil.upFirst(primaryKeyColumn.getProperty()));
      map.put("primaryKeyType", primaryKeyColumn.getType());
    } catch (Exception e) {
      map.put("primaryKeyName", "id");
      map.put("primaryKeyType", "Long");
    }
    map.put("tableName", tableName);
    List<String> importList = getImportList(primaryKeyColumn, false, true);
    importList.add(PathHolder.impt(GenerateType.PO, doClassName));
    importList.add(PathHolder.impt(GenerateType.DAO, daoClassName));
    importList.add(PathHolder.impt(GenerateType.Query, queryClassName));
    importList.add(PathHolder.impt(GenerateType.BaseDAO, GenerateType.BaseDAO.getName()));
    map.put("importList", importList);
    Setting setting = SettingManager.get();
    map.put("daoUseSequence", setting.isDaoUseSequence());
    map.put("daoLogicDelete", setting.isDaoLogicDelete());
    return generate(map);
  }

  @Override
  public Code getCode() {
    return Code.JAVA;
  }

  @Override
  public GenerateType getGenerateType() {
    return GenerateType.DAOImpl;
  }
}
