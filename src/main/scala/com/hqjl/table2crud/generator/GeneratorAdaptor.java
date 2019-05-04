package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.constant.PathType;
import com.hqjl.table2crud.domain.Code;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.Setting;
import com.hqjl.table2crud.storage.Env;
import com.hqjl.table2crud.storage.SettingManager;
import com.hqjl.table2crud.util.FileWriter;
import com.hqjl.table2crud.util.NameUtil;
import com.hqjl.table2crud.util.PathHolder;
import java.util.List;

/**
 *
 */
public class GeneratorAdaptor {

  private String tableName;
  private String path;
  private String code;
  private Column primaryColumn;
  private List<Column> doColumnList;
  private List<Column> queryColumnList;
  private CodeAbstractGenerator generator;


  public GeneratorAdaptor() {
  }

  public GeneratorAdaptor(String tableName, String path, String code) {
    if (!path.endsWith(Env.sp) && !path.endsWith("/")) {
      path += "/";
    }
    path = path.replaceAll("\\" + Env.sp, "/");
    this.tableName = tableName;
    this.code = code;
    this.path = path;
    PathHolder.init(path, Code.get(code));
    if (code.equals("JAVA")) {
      setGenerator(new JavaGenerator());
    } else if (code.equals("SCALA")) {
      setGenerator(new ScalaGenerator());
    }
  }

  public static String getJavaPath(String scalaPath) {
    String markScala = "/src/main/scala";
    String markJava = "/src/main/java";
    int i = scalaPath.indexOf(markScala);
    if (i > -1) {
      return scalaPath.replace(markScala, markJava);
    } else {
      return scalaPath.replaceAll("\\/scala\\/", "/java/");
    }
  }

  public void setColumnList(List<Column> doColumnList, List<Column> queryColumnList) {
    this.doColumnList = changeEncoding(doColumnList);
    this.queryColumnList = changeEncoding(queryColumnList);
  }

  private List<Column> changeEncoding(List<Column> columnList) {
    return columnList;
  }

  private String path(GenerateType generateType) {
    String ph = PathHolder.resourcesPath + generateType.getPath();
    if (PathType.SRC.equals(generateType.getPathType())) {
      ph = path + generateType.getPath();
    } else if (PathType.RESOURCES.equals(generateType.getPathType())) {
      ph = PathHolder.resourcesPath + generateType.getPath();
    }
    return ph;
  }


  public boolean generateDO() {
    String name = name(GenerateType.PO);
    String path = path(GenerateType.PO);
    String content = generator.generateDO(tableName, name, doColumnList);
    return FileWriter.write(path, addExt(name), r(content));
  }

  public boolean generateDAO() {
    String name = name(GenerateType.DAO);
    String path = path(GenerateType.DAO);
    String s = generator.generateDAO(name(GenerateType.PO), name(GenerateType.Query), name, primaryColumn);
    return FileWriter.write(path, addExt(name), r(s));
  }


  public boolean generate(GenerateType generateType) {
    String name = name(generateType);
    String path = path(generateType);
    String s = generator.generateContent(GenerateFileModel.apply(tableName, getDoColumnList().toArray(new Column[0]), generateType, null));
    return FileWriter.write(path, addExt(name), r(s));
  }

  public boolean generateManager() {
    String name = name(GenerateType.Manager);
    String path = path(GenerateType.Manager);
    Setting setting = SettingManager.get();
    String s = generator
        .generateManager(setting.isManagerUseBO() ? name(GenerateType.BO) : name(GenerateType.PO), name(GenerateType.Query), name,
            primaryColumn);
    return FileWriter.write(path, addExt(name), r(s));
  }

  public boolean generateManagerImpl() {
    String name = name(GenerateType.ManagerImpl);
    String path = path(GenerateType.ManagerImpl);
    String s = generator
        .generateManagerImpl(name(GenerateType.PO), name(GenerateType.BO), name(GenerateType.Query), name(GenerateType.Transfer),
            name(GenerateType.DAO), name(GenerateType.Manager), name, primaryColumn);
    return FileWriter.write(path, addExt(name), r(s));
  }

  public boolean generateTransfer() {
    String name = name(GenerateType.Transfer);
    String path = path(GenerateType.Transfer);
    String s = generator.generateTransfer(name(GenerateType.PO), name(GenerateType.BO), name(GenerateType.Transfer), doColumnList);
    return FileWriter.write(path, addExt(name), r(s));
  }

  public boolean generateSqlmap() {
    String name = name(GenerateType.SQLMap);
    String path = path(GenerateType.SQLMap);
    String s = generator
        .generateSqlmap(tableName, primaryColumn, name(GenerateType.PO), name(GenerateType.DAO), doColumnList, queryColumnList);
    return FileWriter.write(path, addExtXml(name), r(s));
  }

  public boolean generateDAOXml() {
    String name = GenerateType.DAOXml.getSuffix();
    String path = path(GenerateType.DAOXml);
    String s = generator.generateDAOXml(name(GenerateType.DAO), name(GenerateType.DAOImpl));
    return FileWriter.write(path, addExtXml(name), r(s));
  }

  public boolean generateSQLMapConfigXml() {
    String name = GenerateType.SQLMapConfigXml.getSuffix();
    String path = path(GenerateType.SQLMapConfigXml);
    String s = generator.generateSQLMapConfigXml(tableName);
    return FileWriter.write(path, addExtXml(name), r(s));
  }

  public boolean generatePersistenceXml() {
    String name = GenerateType.PersistenceXml.getSuffix();
    String path = path(GenerateType.PersistenceXml);
    String s = generator.generatePersistenceXml(tableName);
    return FileWriter.write(path, addExtXml(name), r(s));
  }

  public String name(GenerateType type) {
    return NameUtil.name(this.tableName, type);
  }

  public String entityName() {
    return NameUtil.formatName(tableName);
  }

  public String addExt(String name) {
    return name + Code.get(code).getExt();
  }

  public String addExtXml(String name) {
    return name + ".xml";
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public List<Column> getDoColumnList() {
    return doColumnList;
  }

  public void setDoColumnList(List<Column> doColumnList) {
    this.doColumnList = doColumnList;
  }

  public Column getPrimaryColumn() {
    return primaryColumn;
  }

  public void setPrimaryColumn(Column primaryColumn) {
    this.primaryColumn = primaryColumn;
  }

  public List<Column> getQueryColumnList() {
    return queryColumnList;
  }

  public void setQueryColumnList(List<Column> queryColumnList) {
    this.queryColumnList = queryColumnList;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public CodeAbstractGenerator getGenerator() {
    return generator;
  }

  public void setGenerator(CodeAbstractGenerator generator) {
    this.generator = generator;
  }

  public byte[] r(String s) {
    if (s == null) {
      return null;
    }
    return s.getBytes(Env.encodeTo);
  }

  public String s(String s) {
    if (s == null) {
      return null;
    }
    return new String(s.getBytes(Env.encodeTo), Env.encodeFrom);
  }

}
