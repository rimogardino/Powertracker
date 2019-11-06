package com.example.powertracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import com.example.powertracker.room.ExerciseEntity
import com.example.powertracker.room.ExerciseViewModel
import kotlinx.android.synthetic.main.activity_feature_collection.*
import java.text.SimpleDateFormat
import java.util.*

class FeatureCollection : AppCompatActivity() {

    private val sleepTimes = listOf("1","2","3","4","5","6","7","8","9","10")
    private val foodTimes = listOf("Right now","30 minutes","1 hour","2 hours +")
    private val coffee_Q = listOf("Yes","No","Predjasah","Tea")
    private val timeOfDay = listOf("Morning","Noon","Evening")
    private val feelings = listOf("10%","20%","30%","40%","50%","60%","70%","80%","90%","100%")

    private var sleepTimeData : Float = -666f
    private var foodTimeData  : String = "-666"
    private var coffeeData    : String = "-666"
    private var timeOfDayData : String = "-666"
    private var feelingData   : Float = -666f
    val debugDate = ""

    lateinit var viewModel: ExerciseViewModel

    private var dont_update = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature_collection)
        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())+debugDate

        viewModel = ViewModelProviders.of(this).get(ExerciseViewModel::class.java)



        initAutoDropDowns()


        viewModel.getDate(currentDate)?.observe(this, androidx.lifecycle.Observer { exercise ->
            Log.d("Features","observe todaysSession ${exercise}")

            if (exercise == null) {
                val exerciseEntity = ExerciseEntity(currentDate,null,
                    null,null,null,
                    null,null,null,
                    sleepTimeData,foodTimeData,timeOfDayData,1,coffeeData,feelingData,"")
                viewModel.insert(exerciseEntity)
                Log.d("Features","inited the session $currentDate $exercise")
            }
            else if (dont_update) {
                if (exercise.sleep != -666f && exercise.sleep.toInt().toString() in sleepTimes) sleep_atv.text = Editable.Factory().newEditable(sleepTimes[exercise.sleep.toInt() - 1])
                if (exercise.foodTime != "-666") had_food_atv.text = Editable.Factory().newEditable(exercise.foodTime)
                if (exercise.coffee != "-666") coffee_atv.text = Editable.Factory().newEditable(exercise.coffee)
                if (exercise.timeOfDay != "-666") time_of_day_atv.text = Editable.Factory().newEditable(exercise.timeOfDay)
                Log.d("Features","ho feel ${(exercise.howFeel*100).toInt()}%")
                if (exercise.howFeel != -666f && "${(exercise.howFeel*100).toInt()}%" in feelings) feeling_atv.text = Editable.Factory().newEditable(feelings[(exercise.howFeel*10).toInt()-1])
                extra_stuff_et.text = Editable.Factory().newEditable(exercise.extras)

                dont_update = false
            }
        })
    }


    private fun initAutoDropDowns() {

        val arrayAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
            R.id.fcn_tv , sleepTimes)
        sleep_atv.setAdapter(arrayAdapter)
        sleep_atv.onFocusChangeListener = View.OnFocusChangeListener {  _, _ ->
            sleep_atv.showDropDown()
        }

        sleep_atv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })




        val foodAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
            R.id.fcn_tv , foodTimes)
        had_food_atv.setAdapter(foodAdapter)
        had_food_atv.onFocusChangeListener = View.OnFocusChangeListener { _, _ ->
            had_food_atv.showDropDown()
        }

        had_food_atv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })

        val coffeeAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
            R.id.fcn_tv , coffee_Q)
        coffee_atv.setAdapter(coffeeAdapter)
        coffee_atv.onFocusChangeListener = View.OnFocusChangeListener {_, _ ->
            coffee_atv.showDropDown()
        }

        coffee_atv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })


        val timeOfDayAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
            R.id.fcn_tv , timeOfDay)
        time_of_day_atv.setAdapter(timeOfDayAdapter)
        time_of_day_atv.onFocusChangeListener = View.OnFocusChangeListener {_, _ ->
            time_of_day_atv.showDropDown()
        }

        time_of_day_atv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })

        val feelindAdapter = ArrayAdapter<String> (this, R.layout.simple_list_item_1,
            R.id.fcn_tv , feelings)
        feeling_atv.setAdapter(feelindAdapter)
        feeling_atv.onFocusChangeListener = View.OnFocusChangeListener {_, _ ->
            feeling_atv.showDropDown()
        }

        feeling_atv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })

        extra_stuff_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                collectData()
            }
        })

    }


    fun session(view: View) {
        collectData()

        val intent = Intent(this, Session::class.java)
        startActivity(intent)
    }

    private fun collectData() {
        val currentDate = SimpleDateFormat(this.getString(R.string.dataFormat)).format(Date())+debugDate

        if (sleep_atv.text.isNotEmpty()) sleepTimeData = sleep_atv.text.toString().toFloat()
        if (had_food_atv.text.isNotEmpty()) foodTimeData = had_food_atv.text.toString()
        if (coffee_atv.text.isNotEmpty()) coffeeData = coffee_atv.text.toString()
        if (time_of_day_atv.text.isNotEmpty()) timeOfDayData = time_of_day_atv.text.toString()
        if (feeling_atv.text.isNotEmpty()) feelingData = (feelings.indexOf(feeling_atv.text.toString()) + 1) * 0.1f

        val exEnty = ExerciseEntity(currentDate,null,
            null,null,null,
            null,null,null,
            sleepTimeData,foodTimeData,timeOfDayData,1,coffeeData,feelingData, extra_stuff_et.text.toString())
        viewModel.updateFeatures(exEnty)

    }
}
