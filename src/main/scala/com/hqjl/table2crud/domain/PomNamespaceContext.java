package com.hqjl.table2crud.domain;

import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 *
 */

public class PomNamespaceContext implements NamespaceContext {

  @Override
  public String getNamespaceURI(String prefix) {
    if (prefix == null) {
      throw new NullPointerException("Null prefix");
    } else if ("ns".equals(prefix)) {
      return "http://maven.apache.org/POM/4.0.0";
    } else if ("xsi".equals(prefix)) {
      return "http://www.w3.org/2001/XMLSchema-instance";
    } else if ("schema".equals(prefix)) {
      return "http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd";
    } else if ("xml".equals(prefix)) {
      return XMLConstants.XML_NS_URI;
    }
    return XMLConstants.DEFAULT_NS_PREFIX;
  }

  @Override
  public String getPrefix(String namespaceURI) {
    return null;
  }

  @Override
  public Iterator getPrefixes(String namespaceURI) {
    return null;
  }
}
