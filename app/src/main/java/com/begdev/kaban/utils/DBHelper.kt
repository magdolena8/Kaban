package com.begdev.kaban.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.begdev.kaban.model.TaskModel
import com.begdev.kaban.model.TrackedModel
import java.text.SimpleDateFormat

class DBHelper(context: Context) : SQLiteOpenHelper(context, "tracker.db", null, 1) {

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

    fun getAllTracked(): ArrayList<TrackedModel> {
        val db = this.readableDatabase
        var resultSet: ArrayList<TrackedModel> = ArrayList()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val cursor: Cursor =
            db.query("tracked", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            resultSet.add(
                TrackedModel(
                    TaskModel(cursor.getString(1), cursor.getString(2)),
                    formatter.parse(cursor.getString(3)),
                    cursor.getString(4)
                )
            )
        }
        return resultSet
    }

    fun insertNewTracked(task: TaskModel, deadlineDate: String, color: String): Boolean {
        val db = this.writableDatabase
        try {
            db.execSQL("insert into tracked (title, description, deadline, color, refKey) VALUES (\"${task.title}\", \"${task.description}\", \"${deadlineDate}\", \"${color}\", \"${task.path}\")")
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun updateTracked(tracked: TrackedModel): Boolean {
        val db = this.writableDatabase
        var values: ContentValues = ContentValues()
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        values.put("deadline",formatter.format(tracked.deadline))
        values.put("color", tracked.color)
        try {
            db.update("tracked", values, "title == ?", tracked.task?.let { arrayOf(it.title) })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

    fun deleteTracked(tracked: TrackedModel): Boolean {
        val db = this.writableDatabase
        try {
            db.delete("tracked", "title == ?", tracked.task?.let { arrayOf(it.title) })
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            return false
        }
        return true
    }

}