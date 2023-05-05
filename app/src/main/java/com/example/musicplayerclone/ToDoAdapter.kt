package com.example.musicplayerclone

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ToDoAdapter(val list: List<ToDoModel>) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        return ToDoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(todoModel: ToDoModel) {
            with(itemView) {
                val colors = resources.getIntArray(R.array.random_color)
                val randomColor = colors[Random().nextInt(colors.size)]

                val viewColorTag = findViewById<View>(R.id.viewColorTag)
                val txtShowTitle = findViewById<TextView>(R.id.txtShowTitle)
                val txtShowTask = findViewById<TextView>(R.id.txtShowTask)
                val txtShowCategory = findViewById<TextView>(R.id.txtShowCategory)


                viewColorTag.setBackgroundColor(randomColor)
                txtShowTitle.text = todoModel.title
                txtShowTask.text = todoModel.description
                txtShowCategory.text = todoModel.category
                updateTime(todoModel.time)
                updateDate(todoModel.date)

            }
        }

        @SuppressLint("SimpleDateFormat")
        private fun updateTime(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "h:mm a"
            val sdf = SimpleDateFormat(myformat)
            itemView.findViewById<TextView>(R.id.txtShowTime).text = sdf.format(Date(time))

        }

        @SuppressLint("SimpleDateFormat")
        private fun updateDate(time: Long) {
            //Mon, 5 Jan 2020
            val myformat = "EEE, d MMM yyyy"
            val sdf = SimpleDateFormat(myformat)
            itemView.findViewById<TextView>(R.id.txtShowDate).text = sdf.format(Date(time))

        }
    }
}