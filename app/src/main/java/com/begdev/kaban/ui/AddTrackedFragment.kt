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
import com.begdev.kaban.databinding.FragmentAddTrackedBinding
import com.begdev.kaban.viewmodel.NewTrackedViewModel
import com.begdev.kaban.viewmodel.TaskViewModel

class AddTrackedFragment : Fragment() {
    private lateinit var binding: FragmentAddTrackedBinding
    val args: AddTrackedFragmentArgs by navArgs()
    private val viewModelTask: NewTrackedViewModel by viewModels{
        NewTrackedViewModel.Factory(args.addingTask)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelTask.uiState.value.task = args.addingTask
        binding =
            DataBindingUtil.inflate(layoutInflater, R.layout.fragment_add_tracked, container, false)
        return binding.root
    }

}