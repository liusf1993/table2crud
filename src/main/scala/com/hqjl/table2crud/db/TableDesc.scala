package com.hqjl.table2crud.db

case class TableDesc(field: String,
                     fieldType: String,
                     nullable: Boolean,
                     primary: Boolean,
                     default: String,
                     comment: String)
