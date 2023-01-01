package com.begdev.kaban.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.TableModel
import com.begdev.kaban.model.TaskModel
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class TableUiState(
    var table: TableModel? = null,
    var tasksArray: MutableList<TaskModel>? = arrayListOf(),
)

class TableViewModel(val table: TableModel): ViewModel() {
    private val _uiState = MutableStateFlow(TableUiState())
    val uiState: StateFlow<TableUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore
//    private val tablesRef = db.collection("/tables/${table.key}/tasks")
    private val tasksRef = db.collection(table.path+"/tasks")

    init {
        _uiState.value.table = table

        tasksRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) return@addSnapshotListener
            if (snapshot == null) return@addSnapshotListener
            val documents = snapshot.documents
            val tasks: MutableList<TaskModel> = mutableListOf()
            for (item in documents) {
                if (item.data == null) continue
                //TODO hz как, но надо парсить в объект ProjectModel
                ///////////
//                var qwe:MutableLiveData<Objects> = item.data.get("name")
                val task = item.toObject(TaskModel::class.java)!!
//                task.key = item.id
                task.path = table.path +"/tasks/"
                task.key = item.id
                tasks.add(task)
//                projects.add(item.toObject(ProjectModel::class.java)!!)
//                projects.add(qwe)
                //////////
                _uiState.update { currentState ->
                    currentState.copy(
                        tasksArray = tasks
                    )
                }
            }
        }
    }

    class Factory(private val table: TableModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TableViewModel(table) as T
        }
    }
}