package com.begdev.kaban

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var currentUser : FirebaseUser
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentUser = intent.getParcelableExtra<FirebaseUser>("user") as FirebaseUser

        supportFragmentManager.commit {
            add(R.id.fragmentContainerView, ProjectsFragment())
            setReorderingAllowed(true)
        }


        Log.d("MainActivity", currentUser.displayName.toString())
    }
}