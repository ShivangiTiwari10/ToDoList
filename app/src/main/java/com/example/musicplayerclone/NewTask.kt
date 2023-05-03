package com.example.musicplayerclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.musicplayerclone.databinding.ActivityNewTaskBinding

class NewTask : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {
                R.id.dateEdt -> {
                    setListener()
                }
                R.id.timeEdt -> {
                    setTimeListener()
                }
                R.id.saveBtn -> {
                    saveTodo()
                }
            }
        }
    }

    private fun saveTodo() {
        TODO("Not yet implemented")
    }

    private fun setTimeListener() {
        TODO("Not yet implemented")
    }

    private fun setListener() {
        TODO("Not yet implemented")
    }
}