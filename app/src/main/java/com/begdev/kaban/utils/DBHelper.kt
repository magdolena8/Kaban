package com.begdev.kaban.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context?,
    name: String?,
    version: Int,
    openParams: SQLiteDatabase.OpenParams
) : SQLiteOpenHelper(context, name, version, openParams) {

    val DB_NAME: String = "tracker.db"
    val DB_VERSION: Int = 1


    companion object {
        fun createDatabse(): String {
            return "qwe"
        }

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBContract.sqlCreateTableTasks)
//        SQLiteDatabase.create()
//        sqLiteDatabase.execSQL(DBContract.SQL_CREATE_SUBJECT_TABLE);
//        sqLiteDatabase.execSQL(DBContract.SQL_CREATE_GROUPS_TABLE);
//        sqLiteDatabase.execSQL(DBContract.SQL_CREATE_STUDENTS_TABLE);


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}