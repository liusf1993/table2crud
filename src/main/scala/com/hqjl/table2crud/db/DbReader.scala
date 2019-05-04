package com.hqjl.table2crud.db

import java.lang
import java.sql.{Connection, DriverManager, ResultSet}

import com.hqjl.table2crud.db.DbReader.dbReader

import scala.collection.mutable.{ArrayBuffer, ListBuffer}


class DbReader private(
                        val url: String,
                        val username: String,
                        val password: String,
                        val dbName: String) {

  private var connection: Connection = _

  def connect: Connection = {
    Class.forName("com.mysql.jdbc.Driver")
    val fullUrl = s"jdbc:mysql://$url/$dbName?autoReconnect=true&useUnicode=true&amp;characterEncoding=UTF-8"
    connection = DriverManager.getConnection(fullUrl, username, password)
    connection
  }

  def readTables(tableLike: String = ""): Array[Array[AnyRef]] = {
    getTables(tableLike).map(x => Array[AnyRef](x, new lang.Boolean(false))).toArray
  }

  def getTables(tableLike: String = ""): List[String] = {
    val statement = connection.createStatement()
    val result: ResultSet = statement.executeQuery(s"show tables like '%$tableLike%'")
    val tables: ArrayBuffer[String] = new ArrayBuffer[String]
    while (result.next()) {
      tables.append(result.getString(1))
    }
    statement.close()
    tables.toList
  }

  def readColumns(table: String): Array[TableDesc] = {
    dbReader.describeTable(table).toArray
  }

  def describeTable(table: String): List[TableDesc] = {
    val statement = connection.createStatement()
    val result = statement.executeQuery(s" show full columns from $table")
    val tableDesc = new ListBuffer[TableDesc]
    while (result.next()) {
      tableDesc.append(TableDesc(
        result.getString("Field"),
        result.getString("Type"),
        "Yes" == result.getString("Null"),
        "PRI" == result.getString("Key"),
        result.getString("Default"),
        result.getString("Comment")

      ))
    }
    statement.close()
    tableDesc.toList

  }

  override def equals(other: Any): Boolean = other match {
    case that: DbReader =>
      (that canEqual this) &&
        url == that.url &&
        username == that.username &&
        password == that.password &&
        dbName == that.dbName
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[DbReader]

  override def hashCode(): Int = {
    val state = Seq(url, username, password, dbName)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }
}

object DbReader {
  var dbReader: DbReader = _

  def apply(
             url: String,
             username: String,
             password: String,
             dbName: String): DbReader = {
    val newDbReader = new DbReader(url, username, password, dbName)
    if (dbReader == null) {
      dbReader = newDbReader
      dbReader.connect
    }
    if (!newDbReader.equals(dbReader)) {
      if (dbReader.connection != null) {
        dbReader.connection.close()
      }
      dbReader = newDbReader
    }
    if (dbReader.connection == null) {
      dbReader.connect
    }
    if (dbReader.connection.isClosed) {
      dbReader.connect
    }
    dbReader
  }

}
