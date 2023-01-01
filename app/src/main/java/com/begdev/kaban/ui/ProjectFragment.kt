package com.begdev.kaban.ui

import android.os.Bundle
import android.util.Log
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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.R
import com.begdev.kaban.adapter.TablesAdapter
import com.begdev.kaban.databinding.FragmentProjectBinding
import com.begdev.kaban.model.TableModel
import com.begdev.kaban.viewmodel.ProjectViewModel
import kotlinx.coroutines.launch

class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding
    val args: ProjectFragmentArgs by navArgs()
    val tablesAdapter = TablesAdapter()
    private val viewModelProject: ProjectViewModel by viewModels{
        ProjectViewModel.Factory(args.selectedProject)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        binding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.my_wallet, null, false);
////        setContentView(binding.getRoot());
//
//
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_project, container, false)
        binding.vmProject = viewModelProject
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tablesAdapter.setOnItemClickListener(object: TablesAdapter.onItemCLickListener{
            override fun onItemClick(position: Int) {
                val table: TableModel = tablesAdapter.currentList.get(position)
                Log.d("TAAG", position.toString())
//                findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToProjectFragment(qwe))
                findNavController().navigate(ProjectFragmentDirections.actionProjectFragmentToTableFragment(table))

            }
        })
        binding.apply {
            tablesRecycler.apply {
                adapter = tablesAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModelProject.uiState.collect {
                    tablesAdapter.submitList(it.tablesArray)
                }
            }
        }


    }


}