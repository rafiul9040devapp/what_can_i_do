package com.rafiul.whatcanido.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task


class TaskViewModel (private val application: Application) : AndroidViewModel(application) {

    private val repository = DefaultTaskRepository.getInstance(application)

    private val _items: LiveData<List<Task>> = repository.getAllTask()
    val item: LiveData<List<Task>> = _items

    private val _openTask = MutableLiveData<Task>()
    val openTask: LiveData<Task> = _openTask

    fun openTask(task:Task){
        _openTask.postValue(task)
    }
}