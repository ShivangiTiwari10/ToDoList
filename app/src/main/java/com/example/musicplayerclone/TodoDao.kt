package com.example.musicplayerclone

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert()
    suspend fun insertTask(todoModel: ToDoModel):Long

    @Query("Select * from ToDoModel where isFinished == 0")
    fun getTask(): LiveData<List<ToDoModel>>

    @Query("Update ToDoModel Set isFinished = 1 where id=:uid")
    fun finishTask(uid:Long)

    @Query("Delete from TodoModel where id=:uid")
    fun deleteTask(uid:Long)
}