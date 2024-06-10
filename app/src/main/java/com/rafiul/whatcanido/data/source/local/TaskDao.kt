package com.rafiul.whatcanido.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafiul.whatcanido.data.source.Task

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Query("SELECT * FROM TASKS ORDER BY entry_id DESC")
    fun getAllTask(): LiveData<List<Task>>

    @Query("SELECT * FROM TASKS WHERE entry_id = :taskId")
    suspend fun getTaskByID(taskId: Int): Task?
}