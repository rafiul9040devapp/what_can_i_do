package com.rafiul.whatcanido.ui.add_edit_task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rafiul.whatcanido.data.source.DefaultTaskRepository
import com.rafiul.whatcanido.data.source.Task
import kotlinx.coroutines.launch

class AddEditTaskViewModel(application: Application) : AndroidViewModel(application) {

    val title = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    private val repository = DefaultTaskRepository.getInstance(application)

    fun saveTask() {
        val currentTitle = title.value.toString()
        val currentDescription = description.value.toString()

        val task = Task(title = currentTitle, description = currentDescription)
        createTask(task)
    }

    private fun createTask(task: Task) {
        viewModelScope.launch {
            repository.saveTask(task)
        }
    }
}