package com.example.musicplayerclone

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayerclone.databinding.ActivityToDoBinding
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ToDoActivity : AppCompatActivity() {
    val list = arrayListOf<ToDoModel>()
    var adapter = ToDoAdapter(list)

    private lateinit var binding: ActivityToDoBinding

    val db by lazy {
        AppDatabase.getDatabase(this)
    }

    @SuppressLint("CutPasteId", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityToDoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@ToDoActivity)
            adapter = this@ToDoActivity.adapter
        }
        initSwipe()

        db.todoDao().getTask().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                list.clear()
                list.addAll(it)
                adapter.notifyDataSetChanged()
            } else {
                list.clear()
                adapter.notifyDataSetChanged()
            }
        })


//        binding.dots.setOnClickListener {
//            val popupMenu = PopupMenu(this, binding.dots)
//            popupMenu.inflate(R.menu.app_bar_menu)
//            popupMenu.setOnMenuItemClickListener {
//                // Do something when a menu item is clicked
//                when (it.itemId) {
//
//
//                    R.id.taskList -> Toast.makeText(
//                        this,
//                        "You clicked on taskList",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//
//                    R.id.addINBatch -> Toast.makeText(
//                        this,
//                        "You clicked on addINBatch",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//
//                    R.id.removeAdds -> Toast.makeText(
//                        this,
//                        "You clicked on removeAdds",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//
//
//                    R.id.moreApps -> Toast.makeText(
//                        this,
//                        "You clicked on moreApps",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    R.id.inVite -> Toast.makeText(
//                        this,
//                        "You clicked on inVite",
//                        Toast.LENGTH_SHORT
//                    ).show()
//
//                    R.id.settings -> Toast.makeText(
//                        this,
//                        "You clicked on settings",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                    R.id.closeApp -> finish()
//
//                }
//                true
//            }
//            popupMenu.show()
//        }

        binding.fb.setOnClickListener {

            val intent = Intent(this, NewTask::class.java)
            startActivity(intent)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun initSwipe() {

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = list[position]

                if (direction == ItemTouchHelper.LEFT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.todoDao().deleteTask(task.id)
                    }
                } else if (direction == ItemTouchHelper.RIGHT) {
                    GlobalScope.launch(Dispatchers.IO) {
                        db.todoDao().finishTask(task.id)
                    }
                }

            }

            override fun onChildDraw(
                canvas: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView

                    val paint = Paint()
                    val icon: Bitmap

                    if (dX > 0) {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.checkpng)
                        paint.color = Color.parseColor("#388E3C")

                        canvas.drawRect(
                            itemView.left.toFloat(), itemView.top.toFloat(),
                            itemView.left.toFloat() + dX, itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.left.toFloat(),
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }

                    else {
                        icon = BitmapFactory.decodeResource(resources, R.mipmap.trash)

                        paint.color = Color.parseColor("#D32F2F")

                        canvas.drawRect(
                            itemView.right.toFloat() + dX, itemView.top.toFloat(),
                            itemView.right.toFloat(), itemView.bottom.toFloat(), paint
                        )

                        canvas.drawBitmap(
                            icon,
                            itemView.right.toFloat() - icon.width,
                            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height.toFloat()) / 2,
                            paint
                        )
                    }

                    viewHolder.itemView.translationX = dX
                } else {
                    super.onChildDraw(
                        canvas,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val item = menu.findItem(R.id.search)
        val searchView = item.actionView as SearchView

        item.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                displayTodo()
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                displayTodo()
                return true
            }


        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    displayTodo(newText)
                }
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)

    }

    @SuppressLint("NotifyDataSetChanged")
    fun displayTodo(newText: String = "") {
        db.todoDao().getTask().observe(this, Observer {
            if(it.isNotEmpty()){
                list.clear()
                list.addAll(
                    it.filter { todo ->
                        todo.title.contains(newText,true)
                    }
                )
                adapter.notifyDataSetChanged()
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }



}