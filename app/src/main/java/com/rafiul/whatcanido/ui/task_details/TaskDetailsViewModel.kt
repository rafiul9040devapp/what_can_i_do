package com.rafiul.whatcanido.ui.task_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task

class TaskDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DefaultTaskRepository.getInstance(application)

     val title = MutableLiveData<String>()
     val description = MutableLiveData<String>()
    fun getTaskById(taskId: Int): LiveData<Task>? {
        return repository.getTaskById(taskId)
    }

}