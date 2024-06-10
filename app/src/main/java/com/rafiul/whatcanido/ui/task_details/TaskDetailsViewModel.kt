package com.rafiul.whatcanido.ui.task_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map

import com.rafiul.whatcanido.data.source.DefaultTaskRepository

class TaskDetailsViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = DefaultTaskRepository.getInstance(application)

    private val _taskId = MutableLiveData<String>()

    val taskId: MutableLiveData<String> = _taskId
    fun getId(taskId:Int){
        _taskId.postValue(taskId.toString())
    }

}