package com.example.musicplayerclone

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import android.widget.Toast
import com.example.musicplayerclone.databinding.ActivityToDoBinding

class ToDoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToDoBinding

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        binding.dots.setOnClickListener {
            val popupMenu = PopupMenu(this, binding.dots)
            popupMenu.inflate(R.menu.app_bar_menu)
            popupMenu.setOnMenuItemClickListener {
                // Do something when a menu item is clicked
                when (it.itemId) {


                    R.id.taskList -> Toast.makeText(
                        this,
                        "You clicked on taskList",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    R.id.addINBatch -> Toast.makeText(
                        this,
                        "You clicked on addINBatch",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    R.id.removeAdds -> Toast.makeText(
                        this,
                        "You clicked on removeAdds",
                        Toast.LENGTH_SHORT
                    )
                        .show()


                    R.id.moreApps -> Toast.makeText(
                        this,
                        "You clicked on moreApps",
                        Toast.LENGTH_SHORT
                    ).show()

                    R.id.inVite -> Toast.makeText(
                        this,
                        "You clicked on inVite",
                        Toast.LENGTH_SHORT
                    ).show()

                    R.id.settings -> Toast.makeText(
                        this,
                        "You clicked on settings",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    R.id.closeApp -> finish()

                }
                true
            }
            popupMenu.show()
        }

        binding.fb.setOnClickListener {

            val intent = Intent(this, NewTask::class.java)
            startActivity(intent)
        }
    }


}