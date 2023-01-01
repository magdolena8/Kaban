package com.begdev.kaban.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.begdev.kaban.R
import com.begdev.kaban.databinding.FragmentCreateTaskBinding
import com.begdev.kaban.databinding.FragmentProjectBinding
import com.begdev.kaban.viewmodel.TaskViewModel

class CreateTaskFragment : Fragment() {
    private lateinit var binding: FragmentCreateTaskBinding
    private val args: CreateTaskFragmentArgs by navArgs()
    private val viewModelTask: TaskViewModel by viewModels{
        TaskViewModel.Factory(args.editingTable)
    }
//    val args: ProjectFragmentArgs by navArgs()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_create_task, container, false)
        binding.vmNewTask = viewModelTask
        return binding.root
    }
}