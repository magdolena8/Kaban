package com.begdev.kaban.ui

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.begdev.kaban.databinding.FragmentAddTrackedBinding
import com.begdev.kaban.navigation.NavigationCommand
import com.begdev.kaban.viewmodel.NewTrackedViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class AddTrackedFragment : Fragment() {
    private lateinit var binding: FragmentAddTrackedBinding
    val args: AddTrackedFragmentArgs by navArgs()
    private val viewModelTask: NewTrackedViewModel by viewModels {
        NewTrackedViewModel.Factory(args.addingTask)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelTask.uiState.value.task = args.addingTask
        binding =
            DataBindingUtil.inflate(layoutInflater, com.begdev.kaban.R.layout.fragment_add_tracked, container, false)
        binding.vmTask = viewModelTask

        binding.calendarDeadLine.setDate(System.currentTimeMillis());
        binding.calendarDeadLine.minDate = System.currentTimeMillis()
        binding.calendarDeadLine.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            val deadline = Date(year - 1900, month, dayOfMonth)
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            viewModelTask.uiState.value.deadlineDate = formatter.format(deadline)

//            val cal: Calendar = Calendar.getInstance()
//            cal.set(Calendar.YEAR, year)
//            cal.set(Calendar.MONTH, month)
//            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            cal.get()
//            val dateRepresentation = cal.time

//            Toast.makeText(context, deadline.toString(), Toast.LENGTH_SHORT).show()

        }

//        binding.spinnerColor.onItemSelectedListener(
//
//
//        )
        binding.spinnerColor.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(com.begdev.kaban.R.array.spinner_colors)
                viewModelTask.uiState.value.color = choose.get(selectedItemPosition)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })



        return binding.root
    }

//    private fun observeNavigation() {
//        projectViewModel.navigation.observe(viewLifecycleOwner) {
//            it.getContentIfNotHandled()?.let { navigationCommand ->
//                handleNavigation(navigationCommand)
//            }
//        }
//    }
//
//    private fun handleNavigation(navCommand: NavigationCommand) {
//        when (navCommand) {
//            is NavigationCommand.ToDirection -> findNavController().navigate(navCommand.directions)
//            is NavigationCommand.Back -> findNavController().popBackStack()
//        }
//    }

}