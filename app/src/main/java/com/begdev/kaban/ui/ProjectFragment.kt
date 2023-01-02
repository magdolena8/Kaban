package com.begdev.kaban.ui

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
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
import com.begdev.kaban.adapter.TablesAdapter
import com.begdev.kaban.databinding.FragmentProjectBinding
import com.begdev.kaban.model.TableModel
import com.begdev.kaban.viewmodel.ProjectViewModel
import kotlinx.coroutines.launch


class ProjectFragment : Fragment() {
    private lateinit var binding: FragmentProjectBinding
    val args: ProjectFragmentArgs by navArgs()
    val tablesAdapter = TablesAdapter()
    private val viewModelProject: ProjectViewModel by viewModels {
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
        activity?.setTitle("Project")
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_project, container, false)
        binding.vmProject = viewModelProject
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tablesAdapter.setOnItemClickListener(object : TablesAdapter.onItemCLickListener {
            override fun onItemClick(position: Int) {
                val table: TableModel = tablesAdapter.currentList.get(position)
                Log.d("TAAG", position.toString())
//                findNavController().navigate(ProjectsListFragmentDirections.actionProjectsListFragmentToProjectFragment(qwe))
                findNavController().navigate(
                    ProjectFragmentDirections.actionProjectFragmentToTableFragment(
                        table
                    )
                )

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

        val menuHost: MenuHost = requireActivity()
//        menuHost.removeMenuProvider()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.menu_project, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.addUser -> {
                        val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clipData = ClipData.newPlainText("text", viewModelProject.project.key)
                        clipboardManager.setPrimaryClip(clipData)
                        Toast.makeText(context, "Invite code copied to clipboard", Toast.LENGTH_LONG).show()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }

}