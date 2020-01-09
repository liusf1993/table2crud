package com.hqjl.table2crud.domain

class DBSettings {
  var ip: String = ""
  var port: String = ""
  var url: String = ""
  var username: String = ""
  var password: String = ""
  var db: String = ""

  def this(url: String, username: String, password: String, db: String) {
    this
    this.url = url
    this.username = username
    this.password = password
    this.db = db
  }

  def this(ip: String, port: String, username: String, password: String, db: String) {
    this
    this.ip = ip
    this.port = port
    this.username = username
    this.password = password
    this.db = db
  }

  def setUrl(url: String): DBSettings = {
    this.url = url
    this
  }

  def getUrl: String = this.url

  def setUsername(username: String): DBSettings = {
    this.username = username
    this
  }

  def getUsername: String = this.username

  def setPassword(password: String): DBSettings = {
    this.password = password
    this
  }

  def getPassword: String = this.password

  def setDb(db: String): DBSettings = {
    this.db = db
    this
  }

  def getDb: String = this.db


}

object DBSettings {
  def apply(url: String = "127.0.0.1:3306", username: String = "root", password: String = "", db: String = "mysql"): DBSettings = {
    new DBSettings(url, username, password, db)
  }
}
