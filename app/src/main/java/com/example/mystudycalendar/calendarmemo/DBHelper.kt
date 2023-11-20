package com.example.mystudycalendar.calendarmemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mystudycalendar.ui.home.MemoFragment

class DBHelper(context: Context) : SQLiteOpenHelper(context, "memo.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table note (id integer primary key autoincrement, memo text not null, datetime integer)"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // 업그레이드 로직 구현 (필요에 따라 추가)
    }
}