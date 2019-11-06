package com.example.powertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.powertracker.RecyclerAdapters.DateInforAdapter
import com.example.powertracker.room.ExerciseViewModel
import kotlinx.android.synthetic.main.activity_date_info.*

class DateInfo : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var excersiseArray: MutableList<MutableList<*>> = mutableListOf()
    private var exerciseVal: MutableList<MutableList<*>> = mutableListOf()

    lateinit var viewModel: ExerciseViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date_info)
        val extras = intent.extras


        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)

        viewModel.getDate(extras?.get("date") as String)
            ?.observe(this, androidx.lifecycle.Observer { session ->


                val dateHeader = tv_date_header
                dateHeader.text = session.date

                Log.d("DateInfo", "date: ${session.date}")
                Log.d("DateInfo", "exercise_1: ${session.exercise_1}")
                //exerciseVal = mutableListOf(mutableListOf(session.date))
                if (isValidExcersise(session.exercise_1)) exerciseVal.add(session.exercise_1!!.toMutableList())
                if (isValidExcersise(session.exercise_2)) exerciseVal.add(session.exercise_2!!.toMutableList())
                if (isValidExcersise(session.exercise_3)) exerciseVal.add(session.exercise_3!!.toMutableList())
                if (isValidExcersise(session.exercise_4)) exerciseVal.add(session.exercise_4!!.toMutableList())
                if (isValidExcersise(session.exercise_5)) exerciseVal.add(session.exercise_5!!.toMutableList())
                if (isValidExcersise(session.exercise_6)) exerciseVal.add(session.exercise_6!!.toMutableList())
                if (isValidExcersise(session.exercise_7)) exerciseVal.add(session.exercise_7!!.toMutableList())
                Log.d("DateInfo", "exerciseVal: ${exerciseVal}")

                excersiseArray.add(exerciseVal)
                Log.d("DateInfo", "excersiseArray: ${excersiseArray}")






                viewManager = LinearLayoutManager(this)

                for (ex in excersiseArray) {
                    viewAdapter = DateInforAdapter(ex)

                    recyclerView = findViewById<RecyclerView>(R.id.rv_exercise_history_list).apply {
                        setHasFixedSize(true)

                        layoutManager = viewManager

                        adapter = viewAdapter
                    }
                }

            })


    }


    private fun isValidExcersise(exercise: List<Any>?) : Boolean {
        //todo This doesn't work
        var exName = exercise.toString().split(",")[0]
        exName = exName.substring(1, exName.length)
        Log.d("DateInfo", "exName: ${exName.length}")
        return !exercise.isNullOrEmpty()

    }
}
