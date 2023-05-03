package com.example.musicplayerclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.musicplayerclone.databinding.ActivityNewTaskBinding

class NewTask : AppCompatActivity() {
    private lateinit var binding: ActivityNewTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}