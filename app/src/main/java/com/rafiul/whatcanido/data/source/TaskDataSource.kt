package com.rafiul.whatcanido.data.source

import androidx.lifecycle.LiveData

interface TaskDataSource {
    suspend fun saveTask(task: Task)
    fun getAllTask(): LiveData<List<Task>>
   suspend fun observeTask(taskId: Int): Task?
}