package com.example.android_simple_apps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.android_simple_apps.InventBicycle;

class Calc : Fragment() {
    private lateinit var state : String;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_calc, container, false)
        var output = view.findViewById<TextView>(R.id.calc_output)
        view.findViewById<Button>(R.id.btn_go_calc_menu).setOnClickListener {
            parentFragmentManager.beginTransaction().replace(R.id.mainframe, Menu()).commit()
        }
        view.findViewById<Button>(R.id.calc_btn0).setOnClickListener { char_input("0", output) }
        view.findViewById<Button>(R.id.calc_btn1).setOnClickListener { char_input("1", output) }
        view.findViewById<Button>(R.id.calc_btn2).setOnClickListener { char_input("2", output) }
        view.findViewById<Button>(R.id.calc_btn3).setOnClickListener { char_input("3", output) }
        view.findViewById<Button>(R.id.calc_btn4).setOnClickListener { char_input("4", output) }
        view.findViewById<Button>(R.id.calc_btn5).setOnClickListener { char_input("5", output) }
        view.findViewById<Button>(R.id.calc_btn6).setOnClickListener { char_input("6", output) }
        view.findViewById<Button>(R.id.calc_btn7).setOnClickListener { char_input("7", output) }
        view.findViewById<Button>(R.id.calc_btn8).setOnClickListener { char_input("8", output) }
        view.findViewById<Button>(R.id.calc_btn9).setOnClickListener { char_input("9", output) }

        view.findViewById<Button>(R.id.calc_btnmul).setOnClickListener { char_input("*", output) }
        view.findViewById<Button>(R.id.calc_btndiv).setOnClickListener { char_input("/", output) }
        view.findViewById<Button>(R.id.calc_btnsub).setOnClickListener { char_input("-", output) }
        view.findViewById<Button>(R.id.calc_btnadd).setOnClickListener { char_input("+", output) }
        view.findViewById<Button>(R.id.calc_btndot).setOnClickListener { char_input(".", output) }

        view.findViewById<Button>(R.id.calc_btndel).setOnClickListener { char_del(output) }
        view.findViewById<Button>(R.id.calc_btnac).setOnClickListener { output_clear(output) }
        view.findViewById<Button>(R.id.calc_btnans).setOnClickListener { output_eval(output) }
        return view
    }
    public fun char_input(digit : String, view :  TextView) {
        var inventbicycle = InventBicycle();
        if (inventbicycle.calc_input_step_validation(view.text.toString() + digit))
            view.text = view.text.toString() + digit;
    }
    public fun char_del(view: TextView) {
        if (view.text.isNotEmpty())
            view.text = view.text.toString().substring(0, view.text.length - 1)
    }
    public fun output_clear(view : TextView) {
        view.text = ""
    }
    public fun output_eval(view : TextView) {
        var operand : String = "[0-9]+([.][0-9]+)?";
        var operators : String = "[+\\-*/]";
        var ptrn : String = operand + "(" + operators + operand + ")?";
        if (Regex(ptrn).matches(view.text.toString()))
        {
            var inventbicycle = InventBicycle();
            var ans =  inventbicycle.calc_parse_eval_ans(view.text.toString());
            if (ans.toInt().toDouble() == ans)
                view.text = ans.toInt().toString();
            else
                view.text = ans.toString();
        }

    }
}