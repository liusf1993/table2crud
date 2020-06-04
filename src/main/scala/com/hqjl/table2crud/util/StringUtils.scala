package com.hqjl.table2crud.util

object StringUtils {
  def isBlank(s: String): Boolean = {
    s == null || s.trim.isEmpty
  }

  def isNotBlank(s: String): Boolean = {
    !isBlank(s)
  }

  def equalsIgnoreCase(s1: String, s2: String): Boolean = {
    s1 == s2 || (s1 != null && s1.equalsIgnoreCase(s2))
  }

  def isEmpty(s: String): Boolean = {
    isBlank(s)
  }
}
