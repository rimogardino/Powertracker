package com.example.powertracker.RecyclerAdapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.powertracker.R

class DateInforAdapter(private val myDataset: MutableList<*>) : RecyclerView.Adapter<DateInforAdapter.DateInfoViewHolder>() {

    class DateInfoViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateInfoViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.date_info_exercise,parent,false) as ConstraintLayout

        return DateInfoViewHolder(
            textView
        )
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: DateInfoViewHolder, position: Int) {

        val exerciseName = myDataset[position].toString().split(",")

        val exerciseHeader = holder.constraintLayout.findViewById(R.id.tv_exercise_header) as TextView
        exerciseHeader.text = exerciseName[0].substring(1, exerciseName[0].length)

        val set1 = holder.constraintLayout.findViewById(R.id.tv_set_1_numbers) as TextView
        set1.text = "${exerciseName[1]} x ${exerciseName[2]}"

        val set2 = holder.constraintLayout.findViewById(R.id.tv_set_1_numbers2) as TextView
        set2.text = "${exerciseName[3]} x ${exerciseName[4]}"

    }


}