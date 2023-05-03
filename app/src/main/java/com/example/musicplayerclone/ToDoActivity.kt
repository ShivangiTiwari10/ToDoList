package com.example.musicplayerclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayerclone.databinding.ActivityToDoBinding

class ToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

    }
}