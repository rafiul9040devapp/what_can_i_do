package com.rafiul.whatcanido.data.source

import android.app.Application
import com.rafiul.whatcanido.data.source.local.LocalDataSource
import com.rafiul.whatcanido.data.source.local.TaskDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultTaskRepository private constructor(application: Application) {
    private val localDataSource: LocalDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    init {
        localDataSource = LocalDataSource(
            TaskDatabase.getInstance(application.applicationContext).taskDao()
        )
    }

    suspend fun saveTask(task: Task) {
        withContext(ioDispatcher) {
            localDataSource.saveTask(task)
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