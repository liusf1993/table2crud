package com.hqjl.table2crud.util;

import com.hqjl.table2crud.domain.Column;
import com.hqjl.table2crud.domain.SqlInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 */
public class SqlAnaly {

  public static String cleanSql(String sql) {
    sql = sql.replaceAll("\\r", "");
    sql = sql.replaceAll("\\n\\n", "\n")
      .replaceAll("(?i)\\n\\s+comment", " comment")
      .replaceAll("(?i)\\n\\s+primary", " primary");
    sql = fixComment(sql);
    sql = fixColumnType(sql);
    sql = sql.replaceAll(",", "\n");
    return sql;
  }

  private static String fixColumnType(String sql) {
    String regStr = "\\s+[a-z]*\\s*\\([^'\\)]*,[^'\\)]*\\)";
    java.util.regex.Matcher mr = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(sql);
    while (mr.find()) {
      String r = mr.group().replaceAll(",", "-");
      sql = sql.replace(mr.group(), r);
    }
    return sql;
  }

  private static String fixComment(String sql) {
    //对于
    String regStr = "comment\\s+'([^']*,[^']*)'";
    java.util.regex.Matcher mr = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(sql);
    while (mr.find()) {
      String r = mr.group().replaceAll(",", "-");
      sql = sql.replace(mr.group(), r);
    }
    regStr = "key\\s+[^(]*\\s+\\([^)]+,[^)]+\\)";
    java.util.regex.Matcher mr1 = Pattern.compile(regStr, Pattern.CASE_INSENSITIVE + Pattern.DOTALL).matcher(sql);
    while (mr1.find()) {
      String r = mr1.group().replaceAll(",", "-");
      sql = sql.replace(mr1.group(), r);
    }
    return sql;
  }


  public static List<String> analyColumnLine(String sql) {
    String regStr = "create\\s+table\\s*(?:if\\s*not\\s*exists)*\\s*[^\\(]*\\s*\\((.*)\\)\\s*[^\\s\\)]*\\s*(?:(ENGINE|comment)*)";
    List<String> matches = Matcher.match(regStr, 1, sql);
    if (matches != null && matches.size() == 1) {
      String lines = matches.get(0);
      lines = lines.replaceAll("\\s+\\n\\s*", "\n").trim();
      String[] la = lines.split("\n");
      return Arrays.asList(la);
    }
    return new ArrayList<>();
  }

  public static String analysisTableName(String sql) {
    String regStr = "create\\s+table\\s*(?:if\\s*not\\s*exists)*(([^\\(]+))\\s*\\(";
    List<String> matches = Matcher.match(regStr, sql);
    if (matches != null && matches.size() == 2) {
      return matches.get(1).replaceAll("`|'|\"|\\s", "");
    }
    return "table";
  }

  public static String analyPrimaryKey(String sql) {
    String regStr = "(,|\\().*\\s+primary\\s+key";
    List<String> matches = Matcher.match(regStr.toLowerCase(), 0, sql);
    if (matches != null && matches.size() == 1) {
      return matches.get(0).substring(1).trim().split(" ")[0];
    }
    return null;
  }


  public static Column getColumn(String line) {
    String regStrP = "[`]*([^`\\s]+)[`]*\\s+(\\S+)\\s*[unsigned\\s]*[not\\s]*[null\\s]*[default\\s]*.*[auto_increament\\s]*\\s*(?:comment\\s*'([^']*)')*";
    String regStr = "comment\\s+'(([^']*))'";
    List<String> l = Matcher.match(regStrP, line.trim());
    List<String> l2 = Matcher.match(regStr, line);
    if (l != null && l.size() == 3) {
      Column c = new Column(l.get(1), l.get(2));
      if (l2 != null && l2.size() == 2) {
        c.setComment(l2.get(1));
      } else {
        c.setComment(l.get(1));
      }
      return c;
    } else {
      return null;
    }
  }


  public static boolean isKeyLine(String line) {
    String regStr = "\\s*((primary|unique)\\s+)*key\\s+";
    List<String> l = Matcher.match(regStr, line);
    if (l != null && l.size() > 0 && !"`".equals(line.trim().substring(0, 1))) {
      return true;
    } else {
      return false;
    }
  }

  public static SqlInfo analysis(String sql) {
    sql = cleanSql(sql);
    List<String> lines = analyColumnLine(sql);
    List<Column> colList = new ArrayList<>();
    for (String line : lines) {
      if (StringUtils.isNotBlank(line)) {
        line = line.trim();
        Column c = getColumn(line);
        if (c != null) {
          c.setPrimaryKey(isKeyLine(line));
          colList.add(c);
        }

      }
    }
    String k = analyPrimaryKey(sql);
    String n = analysisTableName(sql);
    return new SqlInfo(n, k, colList);
  }
}
