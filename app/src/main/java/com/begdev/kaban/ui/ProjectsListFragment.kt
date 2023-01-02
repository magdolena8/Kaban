package com.begdev.kaban.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.ProjectsListViewModel
import com.begdev.kaban.R
import com.begdev.kaban.adapter.ProjectsListAdapter
import com.begdev.kaban.databinding.FragmentProjectsListBinding
import com.begdev.kaban.model.ProjectModel
import kotlinx.coroutines.launch


class ProjectsListFragment : Fragment(R.layout.fragment_projects_list) {
    private lateinit var binding: FragmentProjectsListBinding
    private val vmProjectsList: ProjectsListViewModel by viewModels()
    val projectsAdapter = ProjectsListAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().title = "Projects"
        projectsAdapter.setOnItemClickListener(object : ProjectsListAdapter.onItemCLickListener {
            override fun onItemClick(position: Int) {
                val project: ProjectModel = projectsAdapter.currentList.get(position)
                Log.d("TAAG", position.toString())
                findNavController().navigate(
                    ProjectsListFragmentDirections.actionProjectsListFragmentToProjectFragment(
                        project
                    )
                )
            }
        })


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
                vmProjectsList.uiState.collect {
                    projectsAdapter.submitList(it.projectsArrayList)
                }
            }
        }

        binding.buttonCreateProject.setOnClickListener {
            findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToCreateProjectFragment())
        }

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_projects_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.enterProject -> {
                        val builder = AlertDialog.Builder(context!!)
                        val inflater = layoutInflater
                        val dialogLayout = inflater.inflate(R.layout.alert_enter_project, null)
                        val editText  = dialogLayout.findViewById<EditText>(R.id.editText)
                        builder.setView(dialogLayout)
                            .setTitle("Enter Project")
                            .setPositiveButton("OK"){dialog, id ->
                                ProjectModel.enterProject(editText.text.toString())
                            }
                        builder.show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }
}
