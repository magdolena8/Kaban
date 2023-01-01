package com.begdev.kaban.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName


data class TaskModel(
    @get:PropertyName("title")
    @set:PropertyName("title")
    var title: String? = String(),

    @get:PropertyName("description")
    @set:PropertyName("description")
    var description: String? = String(),

    @get:PropertyName("isChecked")
    @set:PropertyName("isChecked")
    var isChecked: Boolean = false,


    @get:PropertyName("path")
    @set:PropertyName("path")
    var path: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (isChecked == true) 1 else 0)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }
}