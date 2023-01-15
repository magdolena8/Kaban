package com.begdev.kaban.utils

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri


class TrackedContentProvider : ContentProvider() {

    val AUTHORITY = "com.begdev.provider.tracked"
    val URI_TRACKED_ALL = 102
    val URI_TRACKED_DATE = 101
    val URI_TRACKED_FILTER = 202
    val URI_TRACKED_NEAREST = 201

    var dbHelper: DBHelper? = null

    private var uriMatcher: UriMatcher? = UriMatcher(UriMatcher.NO_MATCH)

//    companion object {
//        val uriMatcher = UriMatcher.NO_MATCH
//    }

    init {
//        uriMatcher = UriMatcher.NO_MATCH
        uriMatcher?.addURI(AUTHORITY, "tracked", URI_TRACKED_ALL)
        uriMatcher?.addURI(AUTHORITY, "tracked_date" + "/#", URI_TRACKED_DATE)
        uriMatcher?.addURI(AUTHORITY, "tracked_filter", URI_TRACKED_FILTER)
        uriMatcher?.addURI(AUTHORITY, "tracked_nearest", URI_TRACKED_NEAREST)
    }

    override fun onCreate(): Boolean {
        dbHelper = DBHelper(context!!);
        return true;
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {


        val db = dbHelper!!.writableDatabase
        var cursor = db.rawQuery("SELECT * FROM tracked", null)
        when (uriMatcher!!.match(uri)) {
            URI_TRACKED_ALL -> {
                cursor = db.query("tracked", null, null, null, null, null, null)
                return cursor
            }
            URI_TRACKED_DATE -> {
                val trackedDeadline = uri.lastPathSegment
                cursor = db.rawQuery(
                    "SELECT * from tracked WHERE deadline=?",
                    arrayOf(trackedDeadline)
                )
                return cursor
            }
            URI_TRACKED_FILTER -> {
                val groupID = uri.lastPathSegment
                cursor =
                    db.rawQuery("SELECT * FROM tracked order by deadline", null)
                return cursor
            }
            URI_TRACKED_NEAREST -> {
                val idList = uri.pathSegments
                cursor = db.rawQuery("select * from tracked where deadline > DATE('now') order by deadline limit 1", null)
                return cursor
            }
        }
        return cursor





    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }
}