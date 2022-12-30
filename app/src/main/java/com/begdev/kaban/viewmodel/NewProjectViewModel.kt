package com.begdev.kaban.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.begdev.kaban.R
import com.begdev.kaban.navigation.NavigationCommand
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.ui.CreateProjectFragment
import com.begdev.kaban.ui.CreateProjectFragmentDirections
import com.begdev.kaban.ui.MainActivity
import com.begdev.kaban.ui.ProjectsListFragment
import com.begdev.kaban.utils.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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


    fun addTableTest() = viewModelScope.launch {
        val tables: MutableList<String>? = _uiState.value.tablesArrayList;
        tables?.add(_uiState.value.currentEditingTableName.toString());
        _uiState.value.project?.tables?.add(_uiState.value.currentEditingTableName.toString())
    }


    fun addTable(view: View) {
        val tables: MutableList<String>? = _uiState.value.tablesArrayList;
        tables?.add(_uiState.value.currentEditingTableName.toString());
        _uiState.value.project?.tables?.add(_uiState.value.currentEditingTableName.toString())

//        view.findNavController()
        navigate(CreateProjectFragmentDirections.actionCreateProjectFragmentToProjectsListFragment())

    }

    fun createProject(view: View) {
        _uiState.value.project?.createProject();
        Log.d("NEWPROJECTVM", "create project")
//        navigateBack()

//        navigate(CreateProjectFragmentDirections.actionCreateProjectFragmentToProjectsListFragment())
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = Event(NavigationCommand.ToDirection(navDirections))
    }

    fun navigateBack() {
        _navigation.value = Event(NavigationCommand.Back)
    }



}