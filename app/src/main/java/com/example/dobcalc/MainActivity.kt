package com.example.dobcalc

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var txtDate : TextView? = null
    private var txtMinutes : TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSelectDate= findViewById<Button>(R.id.btnSelectDate)

        txtDate = findViewById<TextView>(R.id.datePicked)
        txtMinutes = findViewById<TextView>(R.id.txtMinutes)



        btnSelectDate.setOnClickListener{
            clickDatePicker()
        }
    }


    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance()
        val year =  myCalendar.get(Calendar.YEAR)
        val month =  myCalendar.get(Calendar.MONTH)
        val day  =  myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(this,
            {_, selectedYear, selectedMonth, selectedDayOfMonth ->

                // parse selected date
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1 }/$selectedYear"

                // edit textview and make it visible
                txtDate?.text = selectedDate
                txtDate?.visibility = View.VISIBLE

                // parse
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)

                // if theDate is not empty
                theDate?.let {

                    // time between selected and 1970
                    val selectedDayInMinutes = theDate.time / 60000

                    // Time since 1970
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))

                    // if currentDate is not empty
                    currentDate?.let {
                        val currentDateMinutes = currentDate.time / 60000

                        // Calculate difference
                        val differenceInMinutes = currentDateMinutes - selectedDayInMinutes

                        txtMinutes?.text = differenceInMinutes.toString()
                        txtMinutes?.visibility = View.VISIBLE
                    }
                }

            },
            year,
            month,
            day
        )

        // Set max date to be today
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000
        dpd.show()
    }

}


