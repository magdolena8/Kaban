package com.begdev.kaban.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.PropertyName
import java.util.*


data class TrackedModel (
    @get:PropertyName("task")
    @set:PropertyName("task")
    var task: TaskModel? = null,

    @get:PropertyName("deadline")
    @set:PropertyName("deadline")
    var deadline: Date? = null,

    @get:PropertyName("color")
    @set:PropertyName("color")
    var color: String? = "green",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(TaskModel::class.java.classLoader),
        Date(parcel.readLong()),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(task, flags)
        parcel.writeLong(deadline!!.time)
        parcel.writeString(color)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TrackedModel> {
        override fun createFromParcel(parcel: Parcel): TrackedModel {
            return TrackedModel(parcel)
        }

        override fun newArray(size: Int): Array<TrackedModel?> {
            return arrayOfNulls(size)
        }
    }
}