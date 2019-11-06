package com.example.powertracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    fun session(view: View) {
        val intent = Intent(this, FeatureCollection::class.java)
        startActivity(intent)
    }

    fun history(view: View) {
        val intent = Intent(this, History::class.java)
        startActivity(intent)
    }


}
