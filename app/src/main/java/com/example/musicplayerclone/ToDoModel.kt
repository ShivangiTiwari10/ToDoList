package com.example.musicplayerclone

import androidx.room.PrimaryKey

data class ToDoModel(

    var title:String,
    var description:String,
    var category: String,
    var date:Long,
    var time:Long,
    var isFinished : Int = 0,
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0
)
