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

    companion object {
        private const val REQUIRED_TITLE_LENGTH = 6
        private const val NO_TASK_ID = 0
    }

    private val _snackBarMessage = MutableLiveData<Int>()
    val snackBarMessage: LiveData<Int> get() = _snackBarMessage

    private val _taskOperationCompleted = MutableLiveData<Event<Unit>>()
    val taskOperationCompleted: LiveData<Event<Unit>> get() = _taskOperationCompleted

    private val _btnName = MutableLiveData<String>()
    val btnName: LiveData<String> get() = _btnName

    private var currentTaskId = NO_TASK_ID

    init {
        updateButtonLabel()
    }

    fun getTaskById(taskId: Int): LiveData<Task>? {
        currentTaskId = taskId
        updateButtonLabel()
        return repository.getTaskById(taskId)
    }

    private fun updateButtonLabel() {
        _btnName.value = if (currentTaskId == NO_TASK_ID) {
            getApplication<Application>().getString(R.string.create_task)
        } else {
            getApplication<Application>().getString(R.string.update_task)
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
        if (currentTaskId == NO_TASK_ID) {
            createTask(task)
        } else {
            updateTask(task)
        }
        _taskOperationCompleted.postValue(Event(Unit))
    }

    private fun isValidTask(currentTitle: String?, currentDescription: String?): Boolean {
        return when {
            currentTitle.isNullOrEmpty() || currentDescription.isNullOrEmpty() -> {
                _snackBarMessage.postValue(R.string.empty_task_message)
                false
            }

            currentTitle.toTrimString().length < REQUIRED_TITLE_LENGTH -> {
                _snackBarMessage.postValue(R.string.title_must_be_6_char_or_more)
                false
            }

            else -> true
        }
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.saveTask(task)
            } catch (e: Exception) {
                _snackBarMessage.postValue(R.string.unable_to_create_task)
            }
        }
    }

    private fun updateTask(task: Task) {
        viewModelScope.launch {
            try {
                repository.editTask(task)
            } catch (e: Exception) {
                _snackBarMessage.postValue(R.string.unable_to_update_task)
            }
        }
    }
}