package com.rafiul.whatcanido.data.source

interface TaskDataSource {
    suspend fun saveTask(task: Task)
}