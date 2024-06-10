package com.rafiul.whatcanido.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import com.rafiul.whatcanido.data.source.local.LocalDataSource
import com.rafiul.whatcanido.data.source.local.TaskDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultTaskRepository private constructor(application: Application) {
    private val localDataSource: LocalDataSource = LocalDataSource(
        TaskDatabase.getInstance(application.applicationContext).taskDao()
    )
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun saveTask(task: Task) {
        withContext(ioDispatcher) {
            localDataSource.saveTask(task)
        }
    }

    fun getAllTask(): LiveData<List<Task>> {
        return localDataSource.getAllTask()
    }

    suspend fun observeTaskById(taskId: Int): Task? {
        return withContext(ioDispatcher) {
            localDataSource.observeTask(taskId)
        }
    }

    companion object {
        private var repository: DefaultTaskRepository? = null

        @Synchronized
        fun getInstance(application: Application): DefaultTaskRepository {
            return if (repository == null) {
                repository = DefaultTaskRepository(application)

                repository as DefaultTaskRepository
            } else {
                repository as DefaultTaskRepository
            }
        }
    }
}