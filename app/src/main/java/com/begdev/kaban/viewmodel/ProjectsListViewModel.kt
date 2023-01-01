package com.begdev.kaban

import androidx.lifecycle.ViewModel
import com.begdev.kaban.model.ProjectModel
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*


data class ProjectsUiState(
    var projectsArrayList: MutableList<ProjectModel>? = arrayListOf()

)

class ProjectsListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProjectsUiState())
    val uiState: StateFlow<ProjectsUiState> = _uiState.asStateFlow()

    private val db = Firebase.firestore
    private val projRef = db.collection("projects")
    //TODO: брать из бд радельно свои и чужие проекты
    init {
        //TODO: refactor -> make class for db operations
        projRef.addSnapshotListener(MetadataChanges.INCLUDE) { snapshot, e ->
            if (e != null) return@addSnapshotListener
            if (snapshot == null) return@addSnapshotListener
            val documents = snapshot.documents
            val projects: MutableList<ProjectModel> = mutableListOf()
            for (item in documents) {
                if (item.data == null) continue
                //TODO hz как, но надо парсить в объект ProjectModel
                ///////////
//                var qwe:MutableLiveData<Objects> = item.data.get("name")
                val project = item.toObject(ProjectModel::class.java)!!
                project.key = item.id
                projects.add(project)
//                projects.add(item.toObject(ProjectModel::class.java)!!)
//                projects.add(qwe)
                //////////
                _uiState.update { currentState ->
                    currentState.copy(
                        projectsArrayList = projects
                    )
                }
            }
        }
    }

}


