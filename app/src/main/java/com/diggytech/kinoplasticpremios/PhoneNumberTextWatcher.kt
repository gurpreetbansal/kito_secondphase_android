package com.imark.cinch.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.widget.EditText

class PhoneNumberTextWatcher(private val edTxt: EditText) : TextWatcher {
    private var isDelete: Boolean = false
   // private lateinit var mPresenter: SignUpPresenter
     init {
        edTxt.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                isDelete = true
            }
            false
        }
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                   after: Int) {
    }

    override fun afterTextChanged(s: Editable) {
        //mPresenter = SignUpPresenter()
        if (isDelete) {
            isDelete = false
            return
        }
        var `val`: String? = s.toString()
        var a: String? = ""
        var b: String? = ""
        var c: String? = ""
        var d: String? = ""
        if (`val` != null && `val`.length > 0) {
            `val` = `val`.replace(".", "")
            if (`val`.length >= 3) {
                a = `val`.substring(0, 3)
            } else if (`val`.length < 3) {
                a = `val`.substring(0, `val`.length)
            }
            if (`val`.length >= 6) {
                b = `val`.substring(3, 6)
                c = `val`.substring(6, `val`.length)
            } else if (`val`.length > 3 && `val`.length < 6) {
                b = `val`.substring(3, `val`.length)
            }
            val stringBuffer = StringBuffer()
            if (a != null && a.length > 0) {
                stringBuffer.append(a)
                if (a.length == 3) {
                    stringBuffer.append(".")
                }
            }
            if (b != null && b.length > 0) {
                stringBuffer.append(b)
                if (b.length == 3) {
                    stringBuffer.append(".")
                }
            }
            if (c != null && c.length > 0) {
                stringBuffer.append(c)
                if (c.length == 3) {
                    stringBuffer.append("-")
                }
            }

            edTxt.removeTextChangedListener(this)
            edTxt.setText(stringBuffer.toString())
            edTxt.setSelection(edTxt.text!!.toString().length)
            edTxt.addTextChangedListener(this)
//            val cpf_length = edTxt.text!!.toString().length
//            val cpf_value = edTxt.text.toString().trim()
//            Log.e("111",cpf_value)
//            if (cpf_length == 14) {
//                Log.e("CPF_VALUE_IS222",cpf_value)
//
//            }




        }

        else {
            edTxt.removeTextChangedListener(this)
            edTxt.setText("")
            edTxt.addTextChangedListener(this)
        }

    }

    companion object {

        private val TAG = PhoneNumberTextWatcher::class.java
                .simpleName
    }




}