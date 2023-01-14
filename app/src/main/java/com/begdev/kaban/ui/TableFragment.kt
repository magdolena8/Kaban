package com.begdev.kaban.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.begdev.kaban.R
import com.begdev.kaban.adapter.TasksAdapter
import com.begdev.kaban.databinding.FragmentTableBinding
import com.begdev.kaban.viewmodel.TableViewModel
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
        activity?.setTitle("Table")
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_table, container, false)
        binding.vmTable = viewModelTable
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            tasksRecycler.apply {

                adapter = tasksAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
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

        binding.buttonCreateTask.setOnClickListener {
            findNavController().navigate(
                TableFragmentDirections.actionTableFragmentToCreateTaskFragment(
                    viewModelTable.table
                )
            )
        }

//        tasksAdapter.setOnItemClickListener(object: TasksAdapter.onItemCLickListener{
//            override fun onItemClick(position: Int) {
//                val project: TaskModel = tasksAdapter.currentList.get(position)
//                Log.d("TAAG", position.toString())
////                findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToProjectFragment(project))
//            }
//        })

        tasksAdapter.setOnItemLongClickListener(object : TasksAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int): Boolean {
                performOptionsMenuClick(position)
                return true
            }

        })
    }

    private fun performOptionsMenuClick(position: Int) {
        val popupMenu = PopupMenu(context , binding.tasksRecycler.getChildAt(position))
        popupMenu.inflate(R.menu.options_menu_task)
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.addToTracked -> {
                        val selectedTask = tasksAdapter.currentList.get(position)
                        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment(selectedTask))
//                        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment())
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.show()
    }


}