package com.example.android_simple_apps

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private val dataSet: Array<Array<String>>, public var context : Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val el: LinearLayout
        init {
            el = view.findViewById(R.id.notes_recycler_element)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.notes_recycler_element, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        var title = viewholder.el.findViewById<TextView>(R.id.notes_title)
        var body = viewholder.el.findViewById<TextView>(R.id.notes_body)
        var id = viewholder.el.findViewById<TextView>(R.id.notes_id)
        id.text = dataSet[position][0]
        title.text = dataSet[position][1]
        body.text = dataSet[position][2]
        if (position % 2 == 0)
            viewholder.el.setBackgroundColor(Color.parseColor("#ffff00"))
        else
            viewholder.el.setBackgroundColor(Color.parseColor("#00ffff"))
        viewholder.el.setOnClickListener {
            (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(R.id.mainframe, NotesEdit.newInstance("update", id.text.toString())).commit()
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}