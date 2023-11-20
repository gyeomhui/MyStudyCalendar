package com.example.mystudycalendar.calendarmemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.mystudycalendar.ui.home.MemoFragment
import com.example.studycalendar.model.Memo

class DBLoader(context: Context){
    private var db: DBHelper

    init {
        db = DBHelper(context)
    }

    fun save(memo: String, datetime: Long){
        val contentValues = ContentValues()
        contentValues.put("memo",memo)
        contentValues.put("datetime",datetime)
        db.writableDatabase.insert("note", null, contentValues)
        db.close()
    }

    fun delete(id: Int ){
        db.writableDatabase.delete("note", "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun update(id: Int, memo: String){
        val contentValues = ContentValues()
        contentValues.put("memo", memo)
        db.writableDatabase.update("note", contentValues, "id=?",arrayOf(id.toString()))
        db.close()
    }

    @SuppressLint("Range")
    fun memoList():ArrayList<Memo>{
        val array = ArrayList<Memo>()
        val sql = "select *from note"
        val cursor:Cursor = db.readableDatabase.rawQuery(sql, null)
        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val memo =cursor.getString(cursor.getColumnIndex("memo"))
            val datetime =cursor.getLong(cursor.getColumnIndex("datetime"))
            val item = Memo(id, memo, datetime)
            array.add(item)
        }
        return array
    }
    @SuppressLint("Range")
    fun memo(id: Int): Memo? {
        val sql = "select * from note where id = " +id
        val cursor = db.readableDatabase.rawQuery(sql, null)
        var itemMemo: Memo? = null
        if(cursor.moveToFirst()){
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val memo =cursor.getString(cursor.getColumnIndex("memo"))
            val datetime =cursor.getLong(cursor.getColumnIndex("datetime"))
            itemMemo = Memo(id, memo, datetime)
        }

        return itemMemo
    }



}
