package com.begdev.kaban.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.ProjectModel
import com.begdev.kaban.model.TableModel
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


data class ProjectUiState(
    var project: ProjectModel?,
    var tablesArray: MutableList<TableModel>? = arrayListOf(),
)
class ProjectViewModel(val project: ProjectModel): ViewModel() {
    private val _uiState = MutableStateFlow(ProjectUiState(null))
    val uiState: StateFlow<ProjectUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore
    private val tablesRef = db.collection("projects/${project.key}/tables")

    init{
        _uiState.value.project = project

        tablesRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) return@addSnapshotListener
            if (snapshot == null) return@addSnapshotListener
            val documents = snapshot.documents
            val tables: MutableList<TableModel> = mutableListOf()
            for (item in documents) {
                if (item.data == null) continue
                //TODO hz как, но надо парсить в объект ProjectModel
                ///////////
//                var qwe:MutableLiveData<Objects> = item.data.get("name")
                val table = item.toObject(TableModel::class.java)!!
                table.key = item.id
                table.path = "projects/${project.key}/tables/${item.id}"
                tables.add(table)
//                projects.add(item.toObject(ProjectModel::class.java)!!)
//                projects.add(qwe)
                //////////
                _uiState.update { currentState ->
                    currentState.copy(
                        tablesArray = tables
                    )
                }
            }
        }




    }

    class Factory(private val project: ProjectModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProjectViewModel(project) as T
        }
    }






}