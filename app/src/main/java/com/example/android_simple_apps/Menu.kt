package com.example.android_simple_apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

class Menu : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View =  inflater.inflate(R.layout.fragment_menu, container, false)
        view.findViewById<Button>(R.id.btn_go_menu_calc).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Calc()).commit()
        }
        return view
    }
}
