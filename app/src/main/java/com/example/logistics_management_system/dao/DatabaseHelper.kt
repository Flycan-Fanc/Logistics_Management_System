package com.example.logistics_management_system.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DatabaseHelper (val context: Context, name: String, version: Int):
    SQLiteOpenHelper(context, name, null, version) {
    private val createUser = "create table User (" +
            "user_id integer primary key autoincrement," + //id,自动增加
            "user_name text," +   //用户名
            "user_password text)"  //用户密码

    private val createWaybill = "create table Waybill (" +
            "waybill_id integer primary key autoincrement," +
            "destination text," +
            "consigner text," +
            "consignerPhone text," +
            "consignee text," +
            "consigneePhone text," +
            "goodsName text," +
            "number text," +
            "paidFreight text," +
            "unpaidFreight text)"


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(createUser)
        db.execSQL(createWaybill)
        Toast.makeText(context, "Create User Table and Waybill Table succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop table if exists User")
        db.execSQL("drop table if exists Waybill")
        onCreate(db)
    }

}
