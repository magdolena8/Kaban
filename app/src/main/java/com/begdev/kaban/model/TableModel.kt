package com.begdev.kaban.model

import com.google.firebase.firestore.PropertyName
import android.os.Parcel
import android.os.Parcelable

data class TableModel(
    @get:PropertyName("key")
    @set:PropertyName("key")
    var key: String? = String(),

    @get:PropertyName("title")
    @set:PropertyName("title")
    var title: String? = String(),

    @get:PropertyName("description")
    @set:PropertyName("description")
    var descriptor: String? = String(),

    @get:PropertyName("tasks")
    @set:PropertyName("tasks")
    var tasks: MutableList<TaskModel>? = arrayListOf(),

    @get:PropertyName("tasksCount")
    @set:PropertyName("tasksCount")
    var tasksCount: Int = 0,

    @get:PropertyName("path")
    @set:PropertyName("path")
    var path: String? = ""


):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("tasks"),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(title)
        parcel.writeString(descriptor)
        parcel.writeInt(tasksCount)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TableModel> {
        override fun createFromParcel(parcel: Parcel): TableModel {
            return TableModel(parcel)
        }

        override fun newArray(size: Int): Array<TableModel?> {
            return arrayOfNulls(size)
        }
    }
}