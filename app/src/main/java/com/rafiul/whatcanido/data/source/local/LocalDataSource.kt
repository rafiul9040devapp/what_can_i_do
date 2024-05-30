package com.rafiul.whatcanido.data.source.local

import com.rafiul.whatcanido.data.source.Task
import com.rafiul.whatcanido.data.source.TaskDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocalDataSource(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {
    override suspend fun saveTask(task: Task) {
        withContext(ioDispatcher) {
            taskDao.insertTask(task)
        }
    }
}