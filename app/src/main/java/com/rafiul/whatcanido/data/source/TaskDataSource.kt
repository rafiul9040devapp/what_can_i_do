package com.rafiul.whatcanido.data.source

import androidx.lifecycle.LiveData

interface TaskDataSource {
    suspend fun saveTask(task: Task)
    suspend fun editTask(task: Task)
    suspend fun deleteTaskById(taskId: Int)
    fun getAllTask(): LiveData<List<Task>>
    fun getTaskById(taskId: Int): LiveData<Task>?
}