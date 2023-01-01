package com.begdev.kaban.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.TableModel
import com.begdev.kaban.model.TaskModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class TaskUiState(
    var task: TaskModel? = TaskModel()
//    var tasksArray: MutableList<TaskModel>? = arrayListOf(),
)

class TaskViewModel(table: TableModel): ViewModel() {
    private val _uiState = MutableStateFlow(TaskUiState())
    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()

    init {
        uiState.value.task?.path = table.path
    }
    fun onCreateTaskClick(view: View) {
        _uiState.value.task?.createTask();
        Log.d("TASK_VM", "create task")
//        navigateBack()

//        navigate(CreateProjectFragmentDirections.actionCreateProjectFragmentToProjectsListFragment())
    }


    class Factory(private val table: TableModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TaskViewModel(table) as T
        }
    }
}