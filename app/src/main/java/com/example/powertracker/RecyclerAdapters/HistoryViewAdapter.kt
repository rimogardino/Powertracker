package com.example.powertracker.RecyclerAdapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.powertracker.DateInfo
import com.example.powertracker.R

class HistoryViewAdapter(private val myDataset: MutableList<MutableList<String>>) : RecyclerView.Adapter<HistoryViewAdapter.HistoryViewHolder>() {

    private lateinit var contextBuffer: Context

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class HistoryViewHolder(val constraintLayout: ConstraintLayout) : RecyclerView.ViewHolder(constraintLayout)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        contextBuffer = parent.context

        val cl = LayoutInflater.from(parent.context)
            .inflate(R.layout.date_view_item,parent,false) as ConstraintLayout

        return HistoryViewHolder(
            cl
        )
    }

    override fun getItemCount(): Int {
        return myDataset.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        Log.d("history Adapter","myDataset: ${myDataset}")

        val date = holder.constraintLayout.getViewById(R.id.tv_date) as TextView
        date.text = myDataset[position][0]
        holder.constraintLayout.setOnClickListener { showMoreInfo(contextBuffer, myDataset[position][0]) }

        val exercises = holder.constraintLayout.getViewById(R.id.tv_exercises) as TextView
        if (myDataset[position].size > 3) exercises.text = "${myDataset[position][1]} / ${myDataset[position][2]} / ${myDataset[position][3]} / ..."

    }

    fun showMoreInfo(context: Context, date: String) {
        val intent = Intent(context, DateInfo::class.java)
        intent.putExtra("date",date)


        startActivity(context, intent, null)
    }
}