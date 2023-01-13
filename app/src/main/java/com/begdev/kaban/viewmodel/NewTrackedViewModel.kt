package com.begdev.kaban.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.TableModel
import com.begdev.kaban.model.TaskModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class TrackedUiState(
    var task: TaskModel? = TaskModel()
)

class NewTrackedViewModel(task: TaskModel) : ViewModel() {
    private val _uiState = MutableStateFlow(TrackedUiState())
    val uiState: StateFlow<TrackedUiState> = _uiState.asStateFlow()

    class Factory(private val task: TaskModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewTrackedViewModel(task) as T
        }
    }
}