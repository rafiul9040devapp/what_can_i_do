package com.rafiul.whatcanido.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rafiul.whatcanido.data.source.Task

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object {
        private const val DATABASE_NAME = "Task_db"
        private var database: TaskDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TaskDatabase {
            return if (database == null) {
                database =
                    Room.databaseBuilder(context, TaskDatabase::class.java, DATABASE_NAME).build()
                database as TaskDatabase
            } else {
                database as TaskDatabase
            }
        }
    }
}