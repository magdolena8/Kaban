package com.begdev.kaban.model

import android.os.Parcel
import android.os.Parcelable

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class ProjectModel (

    @get:PropertyName("key")
    @set:PropertyName("key")
    var key: String? = String(),

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = String(),

    @get:PropertyName("creator")
    @set:PropertyName("creator")
    var creator: String? = FirebaseAuth.getInstance().getCurrentUser()?.email,

    @get:PropertyName("members")
    @set:PropertyName("members")
    var members: MutableList<String>? = arrayListOf(),

    @get:PropertyName("tables")
    @set:PropertyName("tables")
    var tables: MutableList<String>? = arrayListOf(),

    @get:PropertyName("description")
    @set:PropertyName("description")
    var description: String? = String(),

    @get:PropertyName("path")
    @set:PropertyName("path")
    var path: String? = ""

    ):Parcelable
{

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("members"),
        TODO("tables"),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    fun createProject(): Int {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("projects").add(this)
        return 0;
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeString(creator)
        parcel.writeString(description)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectModel> {
        override fun createFromParcel(parcel: Parcel): ProjectModel {
            return ProjectModel(parcel)
        }

        override fun newArray(size: Int): Array<ProjectModel?> {
            return arrayOfNulls(size)
        }
    }
}