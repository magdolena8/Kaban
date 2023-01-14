package com.begdev.kaban.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.TrackedModel
import com.begdev.kaban.utils.DBHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat


data class TrackedUiState(
    var tracked: TrackedModel? = TrackedModel(),
)

class EditTrackedViewModel(val tracked: TrackedModel): ViewModel() {
    private val _uiState = MutableStateFlow(TrackedUiState())
    val uiState: StateFlow<TrackedUiState> = _uiState.asStateFlow()
    init{
        _uiState.value.tracked = tracked
    }

    fun editTracked(view: View) {
        val dbHelper: DBHelper = DBHelper(view.context)
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        _uiState.value.tracked?.let { dbHelper.updateTracked(it) }
    }


    class Factory(private val tracked: TrackedModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTrackedViewModel(tracked) as T
        }
    }
}