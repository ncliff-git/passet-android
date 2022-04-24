package com.github.ncliff.passet.view_components

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.annotation.Nullable
import com.github.ncliff.passet.R

class HorizontalNumberPicker(context: Context, @Nullable attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {
    private var et_number: EditText? = null
    var min: Int = 0
    var max: Int = 0

    init {
        inflate(context, R.layout.numberpicker_horizontal, this)
        et_number = findViewById(R.id.et_number)

        val btn_less: Button = findViewById(R.id.btn_less)
        btn_less.setOnClickListener {
            var newValue = getNumber() - 1
            if (newValue < min)
                newValue = min
            et_number?.setText("$newValue")
        }

        val btn_more: Button = findViewById(R.id.btn_more)
        btn_more.setOnClickListener {
            var newValue = getNumber() + 1
            if (newValue > max)
                newValue = max
            et_number?.setText("$newValue")
        }
    }

    fun getNumber(): Int {
        return et_number?.text.toString().toInt()
    }

    fun setNumber(value: Int) {
        et_number?.setText("$value")
    }
}