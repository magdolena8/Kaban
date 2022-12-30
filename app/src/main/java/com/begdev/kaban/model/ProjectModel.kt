package com.begdev.kaban.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.PropertyName
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


data class ProjectModel (

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
    var description: String = String(),

    )
{
    fun createProject(): Int {
        val db = Firebase.firestore
        val currentUser = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("projects").add(this)
        return 0;
    }
}