package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    private var isLastNumeric: Boolean = false
    private var isLastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvInput = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        tvInput?.append((view as Button).text)
        isLastNumeric = true
        isLastDot = false
    }

    fun onClear(view: View) {
        tvInput?.text = ""
    }

    fun onDot(view: View) {
        if(isLastNumeric && !isLastDot) {
            tvInput?.append(".")
            isLastNumeric = false
            isLastDot = true
        }
    }

    fun onEqual(view: View) {
        if(isLastNumeric) {
            var prefix = ""
            var tvInputValue = tvInput?.text.toString()
            if(tvInputValue.startsWith("-")) {
                prefix = "-"
                tvInputValue = tvInputValue.substring(1)
            }

            try {
                if(tvInputValue.contains("-")) {
                    val splitValue = tvInputValue.split("-")
                    var firstNum = splitValue[0]
                    val secondNum = splitValue[1]
                    if(prefix.isNotEmpty()) {
                        firstNum = prefix + firstNum
                    }
                    tvInput?.text = removeDotFromResult((firstNum.toDouble() - secondNum.toDouble()).toString())
                } else if(tvInputValue.contains("+")) {
                    val splitValue = tvInputValue.split("+")
                    var firstNum = splitValue[0]
                    val secondNum = splitValue[1]
                    if(prefix.isNotEmpty()) {
                        firstNum = prefix + firstNum
                    }
                    tvInput?.text = removeDotFromResult((firstNum.toDouble() + secondNum.toDouble()).toString())
                } else if(tvInputValue.contains("*")) {
                    val splitValue = tvInputValue.split("*")
                    var firstNum = splitValue[0]
                    val secondNum = splitValue[1]
                    if(prefix.isNotEmpty()) {
                        firstNum = prefix + firstNum
                    }
                    tvInput?.text = removeDotFromResult((firstNum.toDouble() * secondNum.toDouble()).toString())
                } else if(tvInputValue.contains("/")) {
                    val splitValue = tvInputValue.split("/")
                    var firstNum = splitValue[0]
                    val secondNum = splitValue[1]
                    if(prefix.isNotEmpty()) {
                        firstNum = prefix + firstNum
                    }
                    tvInput?.text = removeDotFromResult((firstNum.toDouble() / secondNum.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    fun onOperator(view: View){
        tvInput?.text?.let {
            if(isLastNumeric && !isOperatorAdded(it.toString())) {
                tvInput?.append((view as Button).text)
                isLastNumeric = false
                isLastDot = false
            }
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
        }
    }

    private fun removeDotFromResult(result: String): String {
        var value = result
        if(result.contains(".0"))
            value = value.substring(0, value.length - 2)
        return value
    }
}