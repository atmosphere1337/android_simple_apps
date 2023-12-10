package com.example.android_simple_apps

class InventBicycle {
    public fun calc_input_step_validation(input : String) : Boolean
    {
        var state : Int = 0;
        val digits : String = "0123456789";
        val operators : String = "+-/*";
        for (char in input) {
            when (state) {
                0 -> {
                    when (char) {
                        '-' -> state = 1
                        in digits -> state = 2
                        else -> return false
                    }
                }
                1 -> {
                    when (char) {
                        in digits -> state = 2
                        else -> return false
                    }
                }
                2 -> {
                    when (char) {
                        in digits -> state = 2
                        '.' -> state = 3
                        in operators -> state = 4
                        else -> return false
                    }
                }
                3 -> {
                    when (char) {
                        in digits -> state = 3
                        in operators -> state = 4
                        else -> return false
                    }
                }
                4 -> {
                    when (char) {
                        in digits -> state = 4
                        '.' -> state = 5
                        else -> return false
                    }
                }
                5 -> {
                    when (char) {
                        in digits -> state = 5
                        else -> return false
                    }
                }
            }
        }
        return true;
    }
    public fun calc_parse_eval_ans(input : String) : Double {
        var state : Int = 0;
        val digits : String = "0123456789";
        val operators : String = "+-/*";
        var operand1 : Double = 0.0;
        var operand2 : Double = 0.0;
        var operator : Char = '?';
        var opstring1: String = "";
        var opstring2: String = "";
        var transit : Boolean = false;
        for (char in input) {
            when (state) {
                0 -> {
                    opstring1 += char;
                    when (char) {
                        '-' -> { state = 1; transit = false; }
                        in digits -> state = 2
                    }
                }
                1 -> {
                    opstring1 += char;
                    when (char) {
                        in digits -> { state = 2; transit = true; }
                    }
                }
                2 -> {
                    opstring1 += char;
                    when (char) {
                        in digits -> { state = 2; transit = true; }
                        '.' -> { state = 3; transit = false; }
                        in operators -> {   state = 4;
                                            opstring1 = opstring1.substring(0, opstring1.length - 1);
                                            operator = char;
                                            transit = false;
                                        }
                    }
                }
                3 -> {
                    opstring1 += char;
                    when (char) {
                        in digits -> { state = 3; transit = true; }
                        in operators -> {   state = 4;
                                            opstring1 = opstring1.substring(0, opstring1.length - 1);
                                            operator = char;
                                            transit = false;
                                        }
                    }
                }
                4 -> {
                    opstring2 += char;
                    when (char) {
                        in digits -> { state = 4; transit = true; }
                        '.' -> { state = 5; transit = false; }
                    }
                }
                5 -> {
                    opstring2 += char;
                    when (char) {
                        in digits -> { state = 5; transit = true; }
                    }
                }
            }
        }
        if (!transit)
            throw Exception("incomplete sequence");
        operand1 = opstring1.toDouble();
        operand2 = opstring2.toDouble();
        return when (operator) {
            '*' -> operand1 * operand2
            '/' -> operand1 / operand2
            '-' -> operand1 - operand2
            '+' -> operand1 + operand2
            else -> 3.14
        }
    }

}