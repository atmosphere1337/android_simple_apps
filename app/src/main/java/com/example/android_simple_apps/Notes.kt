package com.example.android_simple_apps

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.viewpager2.adapter.StatefulAdapter

class Notes : Fragment() {
    companion object {
        fun newInstance() = Notes()
    }

    private lateinit var viewModel: NotesViewModel

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view: View = inflater.inflate(R.layout.fragment_notes, container, false)

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner)[NotesViewModel::class.java]
        context?.let { viewModel.startup(it) }
        var recycle = view.findViewById<RecyclerView>(R.id.recycle_notes)
        val dataset = viewModel.selectAll()
        var adapter = NotesAdapter(dataset, activity as Context)
        recycle.layoutManager = LinearLayoutManager(context)
        recycle.adapter = adapter

        view.findViewById<Button>(R.id.btn_notes_add_open).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, NotesEdit.newInstance("create", "")).commit()
        }
        view.findViewById<Button>(R.id.btn_go_notes_menu).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Menu()).commit();
        }
        return view;
    }

}