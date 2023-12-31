package com.example.android_simple_apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class NotesEdit : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =inflater.inflate(R.layout.fragment_notes_edit, container, false)

        var vmdb = ViewModelProvider(activity as ViewModelStoreOwner)[NotesViewModel::class.java]
        view.findViewById<Button>(R.id.notes_edit_save).setOnClickListener {
            if (param1 == "create") {
                val title = view.findViewById<EditText>(R.id.notes_edit_title).text.toString()
                val body = view.findViewById<EditText>(R.id.notes_edit_body).text.toString()
                if (title.trim().isNotEmpty() || body.trim().isNotEmpty())
                    vmdb.create(title, body)
            }
            else if (param1 == "update") {
                val title = view.findViewById<EditText>(R.id.notes_edit_title).text.toString()
                val body = view.findViewById<EditText>(R.id.notes_edit_body).text.toString()
                param2?.let { it1 -> vmdb.update(it1, title, body) }
            }
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Notes()).commit()
        }
        view.findViewById<Button>(R.id.notes_edit_delete).setOnClickListener {
            if (param1 == "update") {
                param2?.let { it1 -> vmdb.delete(it1) }
            }
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Notes()).commit()
        }
        if (param1 == "update")
        {
            param2?.let {
                val arrayOne = vmdb.selectOne(it)
                view.findViewById<TextView>(R.id.notes_edit_title).text = arrayOne[1]
                view.findViewById<TextView>(R.id.notes_edit_body).text = arrayOne[2]
            }
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NotesEdit().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}