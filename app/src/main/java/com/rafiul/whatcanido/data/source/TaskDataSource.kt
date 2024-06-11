package com.rafiul.whatcanido.data.source

import androidx.lifecycle.LiveData

interface TaskDataSource {
    suspend fun saveTask(task: Task)
    suspend fun editTask(task: Task)
    fun getAllTask(): LiveData<List<Task>>
    fun getTaskById(taskId: Int): LiveData<Task>?
}