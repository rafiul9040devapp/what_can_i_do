package com.rafiul.whatcanido.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafiul.whatcanido.Event
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task


class TaskViewModel(private val application: Application) : AndroidViewModel(application) {

    private val repository = DefaultTaskRepository.getInstance(application)

    private val _items: LiveData<List<Task>> = repository.getAllTask()
    val items: LiveData<List<Task>> = _items

//    private val _openTaskEvent = MutableLiveData<Event<String>>()
//    val openTaskEvent: LiveData<Event<String>> = _openTaskEvent

    private val _openTask = MutableLiveData<Event<Int>>()
    val openTask: LiveData<Event<Int>> = _openTask

    fun openTaskEvent(taskId: Int) {
        _openTask.value = Event(taskId)
    }
}