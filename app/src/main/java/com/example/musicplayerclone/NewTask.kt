package com.example.musicplayerclone

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import com.example.musicplayerclone.databinding.ActivityNewTaskBinding
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

const val DB_NAME = "todo.db"

class NewTask : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityNewTaskBinding

    private lateinit var myCalendar: Calendar

    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private lateinit var timeSetListener: TimePickerDialog.OnTimeSetListener


    private var finalDate = 0L
    private var finalTime = 0L

    private val labels = arrayListOf("Personal", "Business", "Insurance", "Shopping", "Banking")


    private val db by lazy {
        AppDatabase.getDatabase(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.dateEdt.setOnClickListener(this)
        binding.timeEdt.setOnClickListener(this)
        binding.saveBtn.setOnClickListener(this)
        setUpSpinner()
    }

    private fun setUpSpinner() {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, labels)

        labels.sort()

        binding.spinnerCategory.adapter = adapter
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


    @OptIn(DelicateCoroutinesApi::class)
    private fun saveTodo() {
        val category = binding.spinnerCategory.selectedItem.toString()
        val title = binding.titleInpLay.editText?.text.toString()
        val description = binding.taskInpLay.editText?.text.toString()

        if (category.isEmpty() || title.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    return@withContext db.todoDao().insertTask(
                        ToDoModel(
                            title,
                            description,
                            category,
                            finalDate,
                            finalTime
                        )
                    )
                }
                finish()
            }
        }


    }

    private fun setTimeListener() {
        myCalendar = Calendar.getInstance()
        timeSetListener =
            TimePickerDialog.OnTimeSetListener() { _: TimePicker, hourOfDay: Int, min: Int ->
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                myCalendar.set(Calendar.MINUTE, min)
                updateTime()
            }
        // show the TimePickerDialog
        TimePickerDialog(
            this,
            timeSetListener,
            myCalendar.get(Calendar.HOUR_OF_DAY),
            myCalendar.get(Calendar.MINUTE),
            false
        ).show()

    }

    @SuppressLint("SimpleDateFormat")
    private fun updateTime() {
        val myformat = "h:mm a"
        val sdf = SimpleDateFormat(myformat)
        finalTime = myCalendar.time.time
        binding.timeEdt.setText(sdf.format(myCalendar.time))
    }

    private fun setListener() {
        myCalendar = Calendar.getInstance()


        dateSetListener =
            DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDate()

            }

        // show the DatePickerDialog
        DatePickerDialog(
            this,
            dateSetListener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    @SuppressLint("SimpleDateFormat")
    private fun updateDate() {
        val myformat = SimpleDateFormat("EEE,d MMM yyy HH:mm a")
        finalDate = myCalendar.time.time
        binding.dateEdt.setText(myformat.format(myCalendar.time))

        binding.timeInptLay.visibility = View.VISIBLE

    }

}
