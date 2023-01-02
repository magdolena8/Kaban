package com.begdev.kaban.ui

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.begdev.kaban.R

class ProfileFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            val menuHost: MenuHost = requireActivity()
            menuHost.addMenuProvider(object: MenuProvider{
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_project, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    TODO("Not yet implemented")
                }
            })


        return inflater.inflate(R.layout.fragment_profile, container, false)
    }
}