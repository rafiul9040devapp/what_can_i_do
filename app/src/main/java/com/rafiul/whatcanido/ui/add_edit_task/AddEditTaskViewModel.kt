package com.rafiul.whatcanido.ui.add_edit_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task
import com.rafiul.whatcanido.utils.toTrimString
import kotlinx.coroutines.launch

class AddEditTaskViewModel(application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    private val repository = DefaultTaskRepository.getInstance(application)

    private val REQUIRED_TITLE_LENGTH = 6

    private val _snackBarMessage = MutableLiveData<Int>()
    val snackBarMessage : LiveData<Int>
        get() = _snackBarMessage

    fun saveTask() {
        val currentTitle = title.value
        val currentDescription = description.value

        if (isValidTask(currentTitle, currentDescription)) {
            val task = Task(
                title = currentTitle.toTrimString(),
                description = currentDescription.toTrimString()
            )
            createTask(task)
        }
    }

    private fun isValidTask(currentTitle: String?, currentDescription: String?): Boolean {
        return if (currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty()) {
             _snackBarMessage.postValue(R.string.empty_task_message)
            false
        } else if (currentTitle.toTrimString().length < REQUIRED_TITLE_LENGTH) {
            _snackBarMessage.postValue(R.string.title_must_be_6_char_or_more)
            false
        } else true
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            repository.saveTask(task)
        }
    }
}