package com.example.powertracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.core.view.children
import androidx.lifecycle.ViewModelProviders
import com.example.powertracker.room.ExerciseEntity
import com.example.powertracker.room.ExerciseViewModel
import kotlinx.android.synthetic.main.activity_session.*
import kotlinx.android.synthetic.main.excersize_better.view.*
import kotlinx.android.synthetic.main.exercise.view.*
import kotlinx.android.synthetic.main.exercise.view.reps_1
import kotlinx.android.synthetic.main.exercise.view.reps_2
import kotlinx.android.synthetic.main.exercise.view.reps_3
import kotlinx.android.synthetic.main.exercise.view.reps_4
import kotlinx.android.synthetic.main.exercise.view.reps_5
import kotlinx.android.synthetic.main.exercise.view.spinner_exercise
import kotlinx.android.synthetic.main.exercise.view.weights_1
import kotlinx.android.synthetic.main.exercise.view.weights_2
import kotlinx.android.synthetic.main.exercise.view.weights_3
import kotlinx.android.synthetic.main.exercise.view.weights_4
import kotlinx.android.synthetic.main.exercise.view.weights_5
import java.text.SimpleDateFormat
import java.util.*

class Session : AppCompatActivity() {

    private var sleepTimeData : Float = -666f
    private var foodTimeData  : String = "-666"
    private var coffeeData    : String = "-666"
    private var timeOfDayData : String = "-666"
    private var feelingData   : Float = -666f
    private val REP_BOOL = 0
    private val WEIGHT_BOOL = 1

    private var exercise_buffer_1 = MutableList<Any>(15) { "" }
    private var exercise_buffer_2 = MutableList<Any>(15) { "" }
    private var exercise_buffer_3 = MutableList<Any>(15) { "" }
    private var exercise_buffer_4 = MutableList<Any>(15) { "" }
    private var exercise_buffer_5 = MutableList<Any>(15) { "" }
    private var exercise_buffer_6 = MutableList<Any>(15) { "" }
    private var exercise_buffer_7 = MutableList<Any>(15) { "" }
    private var exercises_buffer = mutableListOf(exercise_buffer_1, exercise_buffer_2, exercise_buffer_3,
        exercise_buffer_4, exercise_buffer_5, exercise_buffer_6, exercise_buffer_7)

    lateinit var repIds: List<Int>
    lateinit var weightIds: List<Int>


    private val repVals = (1..13).toMutableList().map { "$it" }
    private val weightVals = (5..200 step 5).toMutableList().map { "$it" }
    private val debugDate = ""
    lateinit var viewModel: ExerciseViewModel

    private var dont_update = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_session)
        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())+debugDate
        repIds = listOf(exersize_1.reps_1.id, exersize_1.reps_2.id, exersize_1.reps_3.id,
            exersize_1.reps_4.id, exersize_1.reps_5.id, exersize_1.reps_6.id, exersize_1.reps_7.id)

        weightIds = listOf(exersize_1.weights_1.id, exersize_1.weights_2.id, exersize_1.weights_3.id,
            exersize_1.weights_4.id, exersize_1.weights_5.id, exersize_1.weights_6.id,
            exersize_1.weights_7.id)

        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)


        viewModel.getDate(currentDate)?.observe(this, androidx.lifecycle.Observer { session ->
            Log.d("sesh","observe todaysSession ${session}")
            if (session != null && dont_update) {
                Log.d("sesh","session.exercise_1 ${session.exercise_1}")
                Log.d("sesh","session.exercise_1 ${session.exercise_2}")
                if (!session.exercise_1.isNullOrEmpty()) exercises_buffer[0] = session.exercise_1!!.toMutableList()
                if (!session.exercise_2.isNullOrEmpty()) exercises_buffer[1] = session.exercise_2!!.toMutableList()
                if (!session.exercise_3.isNullOrEmpty()) exercises_buffer[2] = session.exercise_3!!.toMutableList()
                if (!session.exercise_4.isNullOrEmpty()) exercises_buffer[3] = session.exercise_4!!.toMutableList()
                if (!session.exercise_5.isNullOrEmpty()) exercises_buffer[4] = session.exercise_5!!.toMutableList()
                if (!session.exercise_6.isNullOrEmpty()) exercises_buffer[5] = session.exercise_6!!.toMutableList()
                if (!session.exercise_7.isNullOrEmpty()) exercises_buffer[6] = session.exercise_7!!.toMutableList()

                for ((ex_i,cl) in session_constraint_layout.children.withIndex()) {
                    for ((set_i,id) in repIds.withIndex()) {
                        val eachRep = cl.findViewById<AppCompatAutoCompleteTextView>(id)
                        eachRep.text = Editable.Factory().newEditable(exercises_buffer[ex_i][set_i*2+1+REP_BOOL].toString())
                    }

                    for ((set_i,id) in weightIds.withIndex()) {
                        val eachWeight = cl.findViewById<AppCompatAutoCompleteTextView>(id)
                        eachWeight.text = Editable.Factory().newEditable(exercises_buffer[ex_i][set_i*2+1+WEIGHT_BOOL].toString())
                    }
                    initSpinners()
                }

            }
            dont_update = false
        })


        initRepDropListeners()
        initWeightDropListeners()


    }

    private fun initSpinners() {
        for ((ex_i,cl) in session_constraint_layout.children.withIndex()) {
            val exerSpinner = cl.spinner_exercise
            val exercises = resources.getStringArray(R.array.exercises)
            val thisExercise = exercises_buffer[ex_i][0]

            if (exercises.indexOf(thisExercise) > -1) {
                exerSpinner.setSelection(exercises.indexOf(thisExercise), true)
                Log.d("sesh","thisExercise is $thisExercise №${ex_i} ${exercises_buffer[ex_i]}")
            }

            exerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    updateData(ex_i)
                }

            }

        }


    }

    private fun initRepDropListeners() {

        for ((ex_i,cl) in session_constraint_layout.children.withIndex()) {
            for ((set_i,id) in repIds.withIndex()) {
                val eachRep = cl.findViewById<AppCompatAutoCompleteTextView>(id)
                //eachRep.text = Editable.Factory().newEditable(exercises_buffer[ex_i][set_i*2+1+REP_BOOL].toString())
                val arrayAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
                    R.id.fcn_tv , repVals)

                //Make the drop downs show up on focusing the view
                eachRep.setAdapter(arrayAdapter)

                eachRep.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                    eachRep.showDropDown()
                }

                //Get the value and send to the database
                var value: String?

                eachRep.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {

                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        value = p0.toString()
                        collectData(REP_BOOL, ex_i, set_i, value)
                    }
                })
            }
        }

    }


    private fun initWeightDropListeners() {

        for ((ex_i,cl) in session_constraint_layout.children.withIndex()) {
            for ((set_i,id) in weightIds.withIndex()) {
                val eachWeight = cl.findViewById<AppCompatAutoCompleteTextView>(id)
                //eachWeight.text = Editable.Factory().newEditable(exercises_buffer[ex_i][set_i*2+1+WEIGHT_BOOL].toString())
                val arrayAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
                    R.id.fcn_tv , weightVals)
                arrayAdapter.setNotifyOnChange(true)

                //Make the drop downs show up on focusing the view
                eachWeight.setAdapter(arrayAdapter)

                eachWeight.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
                    eachWeight.showDropDown()
                }
                //Get the value and send to the database
                var value: String?

                eachWeight.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(p0: Editable?) {

                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        value = p0.toString()
                        collectData(WEIGHT_BOOL, ex_i, set_i, value)
                    }
                })
            }
        }

    }



    private fun collectData(repOrWeight: Int, exerciseInd: Int, setInd: Int, value: String?) {
        if (dont_update) return
        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())+debugDate
        val thisExcBuff = exercises_buffer[exerciseInd]
        val ex_i = session_constraint_layout.children.toList()[exerciseInd]
        val exerciseName: String = ex_i.spinner_exercise.selectedItem.toString()
        thisExcBuff[0] = exerciseName
        thisExcBuff[setInd*2+1+repOrWeight] = value!!

        exercises_buffer[exerciseInd] = thisExcBuff




        val exEnty = ExerciseEntity(currentDate,exercises_buffer[0], exercises_buffer[1],
            exercises_buffer[2],exercises_buffer[3],exercises_buffer[4],exercises_buffer[5],
            exercises_buffer[6],
            sleepTimeData,foodTimeData,timeOfDayData,1,coffeeData,feelingData,"")

        Log.d("sesh", "thisExcBuff $exEnty")
        viewModel.updateExercises(exEnty)

    }

    private fun updateData(exI: Int) {
        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())+debugDate
        val thisExcBuff = exercises_buffer[exI]
        val ex_i = session_constraint_layout.children.toList()[exI]
        val exerciseName: String = ex_i.spinner_exercise.selectedItem.toString()
        thisExcBuff[0] = exerciseName

        val exEnty = ExerciseEntity(currentDate,exercises_buffer[0], exercises_buffer[1],
            exercises_buffer[2],exercises_buffer[3],exercises_buffer[4],exercises_buffer[5],
            exercises_buffer[6],
            sleepTimeData,foodTimeData,timeOfDayData,1,coffeeData,feelingData,"")
        viewModel.updateExercises(exEnty)

    }
}
