package com.example.lifemate.ui.custom

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.example.lifemate.R

class EmailEditText: AppCompatEditText {
    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().isNotEmpty()){
                    if(Patterns.EMAIL_ADDRESS.matcher(s.toString()).matches()){
                        setTextColor(resources.getColor(R.color.black))
                    }
                    else {
                        error = "Invalid Email Addres"
                        setTextColor(resources.getColor(R.color.red))
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }
}