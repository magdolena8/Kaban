package com.begdev.kaban

import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.begdev.kaban.model.ProjectModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.checkerframework.checker.guieffect.qual.UI


data class NewTablesUiState(
    var tablesArrayList: MutableList<String>? = arrayListOf(),
    var currentEditingTableName: String? = "qwe"
)

class NewTableViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(NewTablesUiState())
    val uiState: StateFlow<NewTablesUiState> = _uiState.asStateFlow()

//    val tableStringInput: ObservableField<String> = ObservableField("")

    fun addTable(view: View){
        val projects: MutableList<String>? = _uiState.value.tablesArrayList;
        projects?.add(_uiState.value.currentEditingTableName.toString());
        _uiState.update {
            it.copy(
                tablesArrayList = projects
            )
        }

//        _uiState.value.tablesArrayList?.add(_uiState.value.currentEditingTableName.toString())
//        Log.d("NewTableET", _uiState.value.currentEditingTableName.toString())

    }
}
