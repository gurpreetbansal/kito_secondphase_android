package com.diggytech.kinoplasticpremios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.diggytech.kinoplasticpremios.Login.LoginActivity

import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*
import android.widget.Toast
import android.widget.Spinner
import kotlinx.android.synthetic.main.activity_splash.*


class WelcomeActivity : AppCompatActivity() {
    private lateinit var v: View
    private  var SelectedLanguage: String="en"
    var listOfLanguages = arrayOf("Select Any language","English", "Brazil")

    /*two*/
    var fruits = arrayOf("English", "Brazil")
    var images =intArrayOf(R.mipmap.flag_1, R.mipmap.flag_2)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

//        txt_select_lan.setOnClickListener {
//            txt_select_lan?.visibility = View.GONE
//            //img_select_lan?.visibility = View.GONE
//            languageSpinner?.visibility = View.VISIBLE
//        }
        img_select_lan.setOnClickListener {
            txt_select_lan?.visibility = View.GONE
           // img_select_lan?.visibility = View.GONE
            languageSpinner_welcome.visibility = View.VISIBLE
        }

        languageSpinner_welcome.adapter = MoodArrayAdapter( this,
            listOf( Mood(0, "Selct Any Language"),Mood(R.drawable.flag_one, "English")
                ,Mood(R.drawable.flag_two, "Brazil")
  ))

        languageSpinner_welcome.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        if (position ==0) {
        }
        else  if (position ==1)
        {
            SelectedLanguage="en"
            setLocale("en")
            Log.e("SelectedLanguage","SelectedLanguage" +SelectedLanguage)
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }
        else{//"en", "US"
            SelectedLanguage="pt"

            setLocale("pt")
            Log.e("SelectedLanguage","SelectedLanguage" +SelectedLanguage)
            startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
           // startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
        }

            }


        }
    }

        fun setLocale(lang: String) {
            val myLocale = Locale(lang)
            // val myLocale = Locale("pt", "PT")
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = myLocale
            res.updateConfiguration(conf, dm)
            Log.e("myLocale", "myLocale" + myLocale)
        }





}

