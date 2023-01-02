package com.begdev.kaban.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirestoreRegistrar
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


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
    var path: String? = "",

    @get:PropertyName("key")
    @set:PropertyName("key")
    var key: String? = ""
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeByte(if (isChecked == true) 1 else 0)
        parcel.writeString(path)
        parcel.writeString(key)
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

    fun createTask(){
        val db = Firebase.firestore
//        val result = db.collection(path+"/tasks").add(this)
        val result = db.collection(path+"/tasks").add(this)
        db.document(path!!).update("tasksCount", FieldValue.increment(1))

    }

    fun checkStatus(){
        val db = Firebase.firestore
        val result = db.document(path+"/tasks/"+key).update(mapOf(
            "isChecked" to true
        ))
//        db.document(path!!).update("tasksCount", FieldValue.increment(1))
        db.document(path!!).update("tasksCount", FieldValue.increment(-1))

    }

    fun uncheckStatus(){
        val db = Firebase.firestore
        val result = db.document(path+"/tasks/"+key).update(mapOf(
            "isChecked" to false
        ))
        db.document(path!!).update("tasksCount", FieldValue.increment(1))

    }
}