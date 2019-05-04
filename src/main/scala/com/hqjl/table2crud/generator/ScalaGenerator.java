package com.hqjl.table2crud.generator;

import com.hqjl.table2crud.constant.GenerateType;
import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.generator.s.ScalaBaseDAOGenerator;
import com.hqjl.table2crud.generator.s.ScalaBaseQueryGenerator;
import com.hqjl.table2crud.generator.s.ScalaBoGenerator;
import com.hqjl.table2crud.generator.s.ScalaDAOXmlGenerator;
import com.hqjl.table2crud.generator.s.ScalaDaoGenerator;
import com.hqjl.table2crud.generator.s.ScalaDaoImplGenerator;
import com.hqjl.table2crud.generator.s.ScalaDoGenerator;
import com.hqjl.table2crud.generator.s.ScalaManagerGenerator;
import com.hqjl.table2crud.generator.s.ScalaManagerImplGenerator;
import com.hqjl.table2crud.generator.s.ScalaPersistenceXmlGenerator;
import com.hqjl.table2crud.generator.s.ScalaQueryGenerator;
import com.hqjl.table2crud.generator.s.ScalaSQLMapConfigXmlGenerator;
import com.hqjl.table2crud.generator.s.ScalaSqlmapGenerator;
import com.hqjl.table2crud.generator.s.ScalaTransferGenerator;
import java.util.List;

/**
 *
 */
public class ScalaGenerator extends CodeAbstractGenerator {

  @Override
  public String space() {
    return "  ";
  }

  @Override
  public String generateDO(String tableName, String doClassName, List<Column> columnList) {
    return new ScalaDoGenerator().generate(doClassName, columnList);
  }

  @Override
  public String generateDAO(String doClassName, String queryClassName, String daoClassName, Column primaryKeyColumn) {
    return new ScalaDaoGenerator().generate(doClassName, queryClassName, daoClassName, primaryKeyColumn);
  }

  @Override
  public String generateDAOImpl(String doClassName, String queryClassName, String daoClassName, String daoImplClassName,
      Column primaryKeyColumn, String tableName) {
    return new ScalaDaoImplGenerator().generate(doClassName, queryClassName, daoClassName, daoImplClassName, primaryKeyColumn, tableName);
  }

  @Override
  public String generateQuery(String queryClassName, List<Column> columnQueryList) {
    return new ScalaQueryGenerator().generate(queryClassName, columnQueryList);
  }

  @Override
  public String generateBO(String boClassName, List<Column> columnList) {
    return new ScalaBoGenerator().generate(boClassName, columnList);
  }

  @Override
  public String generateManager(String objClassName, String queryClassName, String managerClassName, Column primaryKeyColumn) {
    return new ScalaManagerGenerator().generate(objClassName, queryClassName, managerClassName, primaryKeyColumn);
  }

  @Override
  public String generateManagerImpl(String doClassName, String boClassName, String queryClassName, String transferClassName,
      String daoClassName, String managerClassName, String managerImplClassName, Column primaryKeyColumn) {
    return new ScalaManagerImplGenerator()
        .generate(doClassName, boClassName, queryClassName, transferClassName, daoClassName, managerClassName, managerImplClassName,
            primaryKeyColumn);
  }

  @Override
  public String generateTransfer(String doClassName, String boClassName, String transferClassName, List<Column> columnList) {
    return new ScalaTransferGenerator().generate(doClassName, boClassName, transferClassName, columnList);
  }

  @Override
  public String generateSqlmap(String tableName, Column primaryKeyColumn, String doClassName, String daoClassName, List<Column> columnList,
      List<Column> columnQueryList) {
    return new ScalaSqlmapGenerator().generate(tableName, primaryKeyColumn, doClassName, daoClassName, columnList, columnQueryList);
  }

  @Override
  public String generateBaseDAO() {
    return new ScalaBaseDAOGenerator().generate();
  }

  @Override
  public String generateBaseQuery() {
    return new ScalaBaseQueryGenerator().generate();
  }

  @Override
  public String generateDAOXml(String daoClassName, String daoImplClassName) {
    return new ScalaDAOXmlGenerator().generate(daoClassName, daoImplClassName);
  }

  @Override
  public String generateSQLMapConfigXml(String tableName) {
    return new ScalaSQLMapConfigXmlGenerator().generate(tableName);
  }

  @Override
  public String generatePersistenceXml(String tableName) {
    return new ScalaPersistenceXmlGenerator().generate(tableName);
  }

  @Override
  public String generate(GenerateType generateType, String className, String entityName, List<String> dependencyClasses) {
    return null;
  }

  @Override
  public String generateContent(GenerateFileModel generateFileModel) {
    return null;
  }
}
