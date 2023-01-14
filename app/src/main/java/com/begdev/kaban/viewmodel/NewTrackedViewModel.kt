package com.begdev.kaban.viewmodel

import android.content.ContentValues
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.begdev.kaban.model.TaskModel
import com.begdev.kaban.utils.DBHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*


data class NewTrackedUiState(
    var task: TaskModel? = TaskModel(),
    var deadlineDate: String = "",
    var color: String = "green"
)

class NewTrackedViewModel(task: TaskModel) : ViewModel() {
    private val _uiState = MutableStateFlow(NewTrackedUiState())
    val uiState: StateFlow<NewTrackedUiState> = _uiState.asStateFlow()

    fun addTracked(view: View) {
        val dbHelper: DBHelper = DBHelper(view.context)
        dbHelper.insertNewTracked(
            _uiState.value.task!!,
            _uiState.value.deadlineDate,
            _uiState.value.color
        )
        addEventCalendarProvider(view)
    }

//    fun editTracked(view: View) {
//        val dbHelper: DBHelper = DBHelper(view.context)
//        val formatter = SimpleDateFormat("yyyy-MM-dd")
//        dbHelper.updateTracked(
//            TrackedModel(
//                _uiState.value.task!!,
//                formatter.parse(_uiState.value.deadlineDate)!!,
//                _uiState.value.color
//            )
//        )
//    }

    private fun addEventCalendarProvider(view: View) {
        val eventUriString = "content://com.android.calendar/events"
        val eventValues = ContentValues()
        eventValues.put("calendar_id", 1);
        eventValues.put("title", _uiState.value.task!!.title);
        eventValues.put("description", _uiState.value.task!!.description);
        val df = SimpleDateFormat("yyyy-MM-dd")
        val startDate = df.parse(_uiState.value.deadlineDate).time
        eventValues.put("dtstart", startDate);
        eventValues.put("duration", "PT1D");
        //        eventValues.put("dtend", endDate);
        eventValues.put("eventStatus", 1)
        eventValues.put("hasAlarm", 0)
        eventValues.put("eventTimezone", TimeZone.getDefault().id)
        val eventUri = view.context.getApplicationContext()
            .getContentResolver()
            .insert(Uri.parse(eventUriString), eventValues)
        val eventID: Long = eventUri?.getLastPathSegment()!!.toLong()
        Toast.makeText(view.context, "Track Added Successfully", Toast.LENGTH_SHORT).show()

//        findNavController().navigate(TableFragmentDirections.actionTableFragmentToAddTrackedFragment(qwe))

    }

    class Factory(private val task: TaskModel) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return NewTrackedViewModel(task) as T
        }
    }
}