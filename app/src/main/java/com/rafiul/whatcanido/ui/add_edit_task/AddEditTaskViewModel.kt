package com.rafiul.whatcanido.ui.add_edit_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rafiul.whatcanido.Event
import com.rafiul.whatcanido.R
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task
import com.rafiul.whatcanido.utils.toTrimString
import kotlinx.coroutines.launch

class AddEditTaskViewModel(application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    private val repository = DefaultTaskRepository.getInstance(application)

    private val requiredTitleLength = 6

    private val _snackBarMessage = MutableLiveData<Int>()
    val snackBarMessage: LiveData<Int>
        get() = _snackBarMessage

    private val _operation = MutableLiveData<Event<Unit>>()
    val operation: LiveData<Event<Unit>> = _operation

    private val noTaskId = 0
    private var currentTaskId = noTaskId
    val btnName = MutableLiveData<String>()

    init {
        btnName.postValue("Create Task")
    }

    fun getTaskById(taskId: Int): LiveData<Task>? {
        currentTaskId = taskId
        changeBtnName()
        return repository.getTaskById(taskId)
    }

    private fun changeBtnName() {
        if (currentTaskId != noTaskId) {
            btnName.postValue("Update Task")
        }
    }

    fun saveTask() {
        val currentTitle = title.value
        val currentDescription = description.value

        if (isValidTask(currentTitle, currentDescription)) {
            val task = Task(
                id = currentTaskId,
                title = currentTitle.toTrimString(),
                description = currentDescription.toTrimString()
            )
            createOrUpdateTask(task)
        }
    }

    private fun createOrUpdateTask(task: Task) {
        if (currentTaskId == noTaskId) {
            createTask(task)
        } else {
            updateTask(task)
        }
        _operation.postValue(Event(Unit))
    }

    private fun isValidTask(currentTitle: String?, currentDescription: String?): Boolean {
        return when {
            currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty() -> {
                _snackBarMessage.postValue(R.string.empty_task_message)
                false
            }
            currentTitle.toTrimString().length < requiredTitleLength -> {
                _snackBarMessage.postValue(R.string.title_must_be_6_char_or_more)
                false
            }
            else -> true
        }
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            repository.saveTask(task)
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.editTask(task)
        }
    }
}