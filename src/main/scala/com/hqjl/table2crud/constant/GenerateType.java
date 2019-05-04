package com.hqjl.table2crud.constant;

import com.hqjl.table2crud.storage.Env;
import java.util.Objects;

/**
 *
 */

public enum GenerateType {
  /**
   *
   */
  PO("PO", "", "", "bean/po/", "bean.po", PathType.SRC, new GenerateType[]{}),
  ReqVO("ReqVO", "", "ReqVO", "bean/vo/input/", "bean.vo.input", PathType.SRC, new GenerateType[]{}),
  ReqDelVO("ReqDelVO", "", "ReqDelVO", "bean/vo/input/", "bean.vo.input", PathType.SRC, new GenerateType[]{}),
  ResVO("ResVO", "", "ResVO", "bean/vo/output/", "bean.vo.output", PathType.SRC, new GenerateType[]{}),
  Transfer("Transfer", "", "Transfer", "bean/bo/transfer/", "bean.bo.transfer", PathType.SRC, new GenerateType[]{PO, ReqVO, ResVO}),
  DAO("DAO", "I", "DAO", "dao/", "dao", PathType.SRC, new GenerateType[]{PO}),
  Manager("Manager", "I", "Manager", "manager/", "manager", PathType.SRC, new GenerateType[]{PO}),
  ManagerImpl("ManagerImpl", "", "ManagerImpl", "manager/impl/", "manager.impl", PathType.SRC, new GenerateType[]{PO, DAO, Manager}),
  Service("Service", "I", "Service", "service/", "service", PathType.SRC, new GenerateType[]{ReqVO, ResVO}),
  ServiceImpl("ServiceImpl", "", "ServiceImpl", "service/impl/", "service.impl", PathType.SRC,
      new GenerateType[]{PO, Manager, Service, ReqVO, ResVO, Transfer}),
  Controller("Controller", "", "Controller", "controller/", "controller", PathType.SRC,
      new GenerateType[]{ReqVO, ResVO, ReqDelVO, Service}),

  BO("BO", "", "BO", "bean/bo/", "bean.bo", PathType.SRC, new GenerateType[]{PO}),

  Query("Query", "Query", "", "dal/query/", "dal.query", PathType.SRC, new GenerateType[0]),
  SQLMap("SQLMap", "_sqlmap", "", "dal/sqlmap/", "dal.dataobject", PathType.RESOURCES, new GenerateType[0]),
  DAOImpl("DAOImpl", "DAOImpl", "", "dal/dao/impl/", "dal.dao.impl", PathType.SRC, new GenerateType[0]),
  BaseDAO("BaseDAO", "BaseDAO", "", "dal/dao/", "dal.dao", PathType.SRC, new GenerateType[0]),
  BaseQuery("BaseQuery", "BaseQuery", "", "dal/query/", "dal.query", PathType.SRC, new GenerateType[0]),
  DAOXml("DAO.Xml", "dao-sample", "", "dal/", "dal.dao.impl", PathType.RESOURCES, new GenerateType[0]),
  SQLMapConfigXml("SQLMap-Config.Xml", "", "sqlmap-config-sample", "dal/", "dal", PathType.RESOURCES, new GenerateType[0]),
  PersistenceXml("Persistence.Xml", "", "persistence-sample", "dal/", "dal.dao", PathType.RESOURCES, new GenerateType[0]);


  private final String prefix;
  private String name;
  private String suffix;
  private String path;
  private String pkg;
  private PathType pathType;
  private GenerateType[] decencies;

  GenerateType(String name, String prefix, String suffix, String path, String pkg, PathType pathType, GenerateType[] decencies) {
    this.name = name;
    this.prefix = prefix;
    this.suffix = suffix;
    this.path = path;
    this.pkg = pkg;
    this.pathType = pathType;
    this.decencies = decencies;
  }

  public static GenerateType get(String name) {
    for (GenerateType c : GenerateType.values()) {
      if (Objects.equals(name.toLowerCase(), c.getName().toLowerCase())) {
        return c;
      }
    }
    return null;
  }

  public String getName() {
    return name;
  }

  public String getSuffix() {
    return suffix;
  }

  public String getPath() {
    //mac linux
    if ("/".equals(Env.sp)) {
      return path;
    } else { //widows
      return path.replaceAll("/", "\\" + Env.sp);
    }
  }

  public String getPrefix() {
    return prefix;
  }

  public String getPkg() {
    return pkg;
  }

  public PathType getPathType() {
    return pathType;
  }

  public GenerateType[] getDecencies() {
    return decencies;
  }

  public GenerateType setDecencies(GenerateType[] decencies) {
    this.decencies = decencies;
    return this;
  }}
