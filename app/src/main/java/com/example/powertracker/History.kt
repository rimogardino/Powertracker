package com.example.powertracker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.powertracker.RecyclerAdapters.HistoryViewAdapter
import com.example.powertracker.room.ExerciseViewModel
import java.text.SimpleDateFormat
import java.util.*

class History : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var dateArray: MutableList<MutableList<String>> = mutableListOf()
    private var dateValz = mutableListOf<String>()

    lateinit var viewModel: ExerciseViewModel
    val FILE_NAME = "DB.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)




        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())

        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)

        val filche = openFileOutput(FILE_NAME, Context.MODE_APPEND)

        viewModel.getAll().observe(this, androidx.lifecycle.Observer {
                exercise ->
            exercise.forEach {
                Log.d("history","date: ${it.date}")
                Log.d("history","exercise_1: ${it.exercise_1}")
                dateValz = mutableListOf(it.date)
                if (!it.exercise_1.isNullOrEmpty()) dateValz.add(it.exercise_1!!.toMutableList()[0].toString())
                if (!it.exercise_2.isNullOrEmpty()) dateValz.add(it.exercise_2!!.toMutableList()[0].toString())
                if (!it.exercise_3.isNullOrEmpty()) dateValz.add(it.exercise_3!!.toMutableList()[0].toString())

                Log.d("history","dateValz: ${dateValz}")

                dateArray.add(dateValz)
                Log.d("history","dateValz: ${dateArray}")

//                history_et.text = history_et.text.toString() + "\n\ndate: ${it.date} + coffee: ${it.coffee}" +
//                        "+ foodTime: ${it.foodTime} + howFeel: ${it.howFeel} + sleep: ${it.sleep}" +
//                        " + exercise_1: ${it.exercise_1} + exercise_2: ${it.exercise_2} " +
//                        "+ exercise_3: ${it.exercise_3}" +
//                        " + exercise_4: ${it.exercise_4} + exercise_5: ${it.exercise_5} + exercise_6: ${it.exercise_6}" +
//                        " + exercise_7: ${it.exercise_7} + ${it.extras}"
            }


//            filche.write(history_et.text.toString().toByteArray())
//
//            Toast.makeText(this,"DB saves at ${filesDir} with name $FILE_NAME", Toast.LENGTH_LONG).show()

            viewManager = LinearLayoutManager(this)
            viewAdapter = HistoryViewAdapter(dateArray)

            recyclerView = findViewById<RecyclerView>(R.id.rv_dates).apply {
                setHasFixedSize(true)

                layoutManager = viewManager

                adapter = viewAdapter
            }

        })




    }
}
