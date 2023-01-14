package com.begdev.kaban.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.begdev.kaban.databinding.FragmentEditTrackedBinding
import com.begdev.kaban.viewmodel.EditTrackedViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class EditTrackedFragment : Fragment() {
    private lateinit var binding: FragmentEditTrackedBinding
    val args: EditTrackedFragmentArgs by navArgs()
    private val viewModelTracked: EditTrackedViewModel by viewModels {
        EditTrackedViewModel.Factory(args.editTrack)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        viewModelTracked.uiState.value.task = args.editTrack
        binding =
            DataBindingUtil.inflate(
                layoutInflater,
                com.begdev.kaban.R.layout.fragment_edit_tracked,
                container,
                false
            )
        binding.vmTracked = viewModelTracked

        binding.calendarDeadLine.setDate(System.currentTimeMillis());
        binding.calendarDeadLine.minDate = System.currentTimeMillis()
        binding.calendarDeadLine.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val msg = "Selected date is " + dayOfMonth + "/" + (month + 1) + "/" + year
            val deadline = Date(year - 1900, month, dayOfMonth)
            val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            viewModelTracked.uiState.value.tracked!!.deadline = deadline
        }
        binding.spinnerColor.setOnItemSelectedListener(object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                val choose = resources.getStringArray(com.begdev.kaban.R.array.spinner_colors)
                viewModelTracked.uiState.value.tracked!!.color = choose.get(selectedItemPosition)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })



        return binding.root
    }

}