package com.begdev.kaban.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.begdev.kaban.R
import com.begdev.kaban.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseUser

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var currentUser: FirebaseUser
        val TAG: String = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        currentUser = intent.getParcelableExtra<FirebaseUser>("user") as FirebaseUser
        Log.d(TAG, currentUser.displayName.toString())
        setContentView(binding.root)
        binding.bottomNavigationViewMain.setupWithNavController(findNavController(R.id.nav_host_fragment))

    }

}