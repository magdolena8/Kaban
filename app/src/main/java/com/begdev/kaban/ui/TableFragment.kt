package com.begdev.kaban.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.begdev.kaban.R
import com.begdev.kaban.databinding.FragmentTableBinding
import com.begdev.kaban.viewmodel.TableViewModel
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.begdev.kaban.adapter.ProjectsListAdapter
import com.begdev.kaban.adapter.TablesAdapter
import com.begdev.kaban.adapter.TasksAdapter
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.model.TaskModel
import kotlinx.coroutines.launch


class TableFragment : Fragment(R.layout.fragment_table) {
    private lateinit var binding: FragmentTableBinding
    val args: TableFragmentArgs by navArgs()
    val tasksAdapter = TasksAdapter()
    private val viewModelTable: TableViewModel by viewModels {
        TableViewModel.Factory(args.selectedTable)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_table, container, false)
        binding.vmTable = viewModelTable
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentTableBinding.bind(view)
//        binding.vmTable = viewModelTable
//        tasksAdapter.setHasStableIds(true)

        binding.apply {
            tasksRecycler.apply {

                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
//                setHasStableIds(true)
//                itemAnimator.changeDuration(0)
//                (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModelTable.uiState.collect {
                    tasksAdapter.submitList(it.tasksArray)
//                    tasksAdapter.setHasStableIds(true)
                }
            }
        }

        binding.buttonCreateTask.setOnClickListener{
            findNavController().navigate(TableFragmentDirections.actionTableFragmentToCreateTaskFragment(viewModelTable.table))
        }

//        tasksAdapter.setOnItemClickListener(object: TasksAdapter.onItemCLickListener{
//            override fun onItemClick(position: Int) {
//                val project: TaskModel = tasksAdapter.currentList.get(position)
//                Log.d("TAAG", position.toString())
////                findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToProjectFragment(project))
//            }
//        })
//

    }


}