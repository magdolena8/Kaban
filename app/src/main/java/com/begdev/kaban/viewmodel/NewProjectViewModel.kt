package com.begdev.kaban.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.begdev.kaban.navigation.NavigationCommand
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class NewProjectUiState(
    var project: ProjectModel? = ProjectModel(),
    var currentEditingTableName: String? = "",
    var tablesArrayList: MutableList<String>? = arrayListOf(),
)

class NewProjectViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(NewProjectUiState())
    val uiState: StateFlow<NewProjectUiState> = _uiState.asStateFlow()

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> get() = _navigation


    fun addTable(view: View) {
        val tables: MutableList<String>? = _uiState.value.project?.tableNamesArray;
        tables?.add(_uiState.value.currentEditingTableName.toString());

    }

    fun createProject(view: View) {
        _uiState.value.project?.createProject();
        Log.d("NEWPROJECTVM", "create project")
    }





}