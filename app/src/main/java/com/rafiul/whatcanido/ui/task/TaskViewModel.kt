package com.rafiul.whatcanido.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rafiul.whatcanido.Event
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task
import kotlinx.coroutines.launch


class TaskViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = DefaultTaskRepository.getInstance(application)

    private val _items: LiveData<List<Task>> = repository.getAllTask()
    val items: LiveData<List<Task>> = _items

    private val _openTask = MutableLiveData<Event<Int>>()
    val openTask: LiveData<Event<Int>> = _openTask
    fun openTaskEvent(taskId: Int) = _openTask.postValue(Event(taskId))

    private val _editTask = MutableLiveData<Event<Int>>()
    val editTask: LiveData<Event<Int>> = _editTask
    fun editTaskEvent(taskId: Int) = _editTask.postValue(Event(taskId))


    private val _showDeleteDialogEvent = MutableLiveData<Event<Int>>()
    val showDeleteDialogEvent: LiveData<Event<Int>> = _showDeleteDialogEvent

    fun onTaskLongClicked(taskId: Int): Boolean {
        _showDeleteDialogEvent.postValue(Event(taskId))
        return true
    }

    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteTask(taskId)
        }
    }
}