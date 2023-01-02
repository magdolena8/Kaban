package com.begdev.kaban.model

import android.os.Parcel
import android.os.Parcelable

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Objects

data class ProjectModel(

    @get:PropertyName("key")
    @set:PropertyName("key")
    var key: String? = String(),

    @get:PropertyName("name")
    @set:PropertyName("name")
    var name: String? = String(),

    @get:PropertyName("creator")
    @set:PropertyName("creator")
    var creator: String? = FirebaseAuth.getInstance().getCurrentUser()?.email,

    @get:PropertyName("participants")
    @set:PropertyName("participants")
    var participants: MutableList<String>? = arrayListOf(),

    @get:PropertyName("tableNamesArray")
    @set:PropertyName("tableNamesArray")
    var tableNamesArray: MutableList<String>? = arrayListOf(),

    @get:PropertyName("description")
    @set:PropertyName("description")
    var description: String? = String(),

    @get:PropertyName("path")
    @set:PropertyName("path")
    var path: String? = ""

) : Parcelable {

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

        fun enterProject(code: String) {
            val db = Firebase.firestore
            db.document("projects/" + code).update(
                "participants",
                FieldValue.arrayUnion(Firebase.auth.currentUser?.email.toString())
            )
        }
    }

    fun createProject() {
        val db = Firebase.firestore
        val projectDataObject = hashMapOf(
            "name" to this.name,
            "creator" to Firebase.auth.currentUser?.email.toString(),
            "description" to this.description,
            "participants" to arrayListOf(Firebase.auth.currentUser?.email.toString()),
        )

        val tablesDataObject = hashMapOf(
            "tables" to arrayListOf<Objects>()
        )

        db.collection("projects").add(projectDataObject).addOnCompleteListener {
            for (qwe in this.tableNamesArray!!) {
                val newTable = hashMapOf(
                    "description" to "",
                    "title" to qwe,
                    "tasksCount" to 0
                )
                it.result.collection("tables").add(newTable)
            }
        }
    }

//    companion object {
//        fun enterProject(code: String) {
//            val db = Firebase.firestore
//            db.document(path!!).update("tasksCount", FieldValue.increment(-1))
//
//        }
//    }
}