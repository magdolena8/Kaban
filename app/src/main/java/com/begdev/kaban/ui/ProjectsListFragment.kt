package com.begdev.kaban.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.ProjectViewModel
import com.begdev.kaban.R
import com.begdev.kaban.adapter.ProjectsAdapter
import com.begdev.kaban.databinding.FragmentProjectsListBinding
import kotlinx.coroutines.launch


class ProjectsListFragment : Fragment(R.layout.fragment_projects_list) {
    private lateinit var binding: FragmentProjectsListBinding
    private val viewModel: ProjectViewModel by viewModels()
    val projectsAdapter = ProjectsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProjectsListBinding.bind(view)
        binding.apply {
            recyclerProjects.apply {
                adapter = projectsAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiState.collect {
                    projectsAdapter.submitList(it.projectsArrayList)
                }
            }
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_projects_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        })

        binding.buttonCreateProject.setOnClickListener {
                findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToCreateProjectFragment())
        }
    }

}
