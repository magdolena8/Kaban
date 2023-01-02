package com.begdev.kaban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.navigation.NavigationCommand
import com.begdev.kaban.R
import com.begdev.kaban.adapter.NewTablesAdapter
import kotlinx.coroutines.launch
import com.begdev.kaban.databinding.FragmentCreateProjectBinding
import com.begdev.kaban.viewmodel.NewProjectViewModel


class CreateProjectFragment : Fragment() {
    private lateinit var binding: FragmentCreateProjectBinding
    private val projectViewModel: NewProjectViewModel by viewModels()
    val newTablesAdapter = NewTablesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.setTitle("Create Project")
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_project, container, false)
        binding.vmNewProject = projectViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            recyclerNewTables.apply {
                adapter = newTablesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        observeNavigation()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                projectViewModel.uiState.collect {
                    newTablesAdapter.submitList(it.project?.tableNamesArray)
                }
            }
        }
    }

    private fun observeNavigation() {
        projectViewModel.navigation.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { navigationCommand ->
                handleNavigation(navigationCommand)
            }
        }
    }

    private fun handleNavigation(navCommand: NavigationCommand) {
        when (navCommand) {
            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
            is NavigationCommand.Back -> findNavController().popBackStack()
        }
    }

}