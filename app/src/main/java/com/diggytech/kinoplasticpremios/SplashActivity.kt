package com.diggytech.kinoplasticpremios

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import com.diggytech.kinoplasticpremios.DashBoard.DashBoardActivity
import com.diggytech.kinoplasticpremios.Login.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*
import java.util.*

class SplashActivity : AppCompatActivity() {
    private var loggedIn: Boolean = false
    private lateinit var v: View
    private  var SelectedLanguage: String="pt"
    var listOfLanguages = arrayOf("Select Any language","English", "Brazil")

    /*two*/
    var fruits = arrayOf("English", "Brazil")
    var images =
        intArrayOf(R.mipmap.flag_1, R.mipmap.flag_2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SelectedLanguage="pt"
        //SelectedLanguage="en"

        setLocale("pt")
        //setLocale("en")
        Log.e("SelectedLanguage","SelectedLanguage" +SelectedLanguage)
      //  startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        /*   FirebaseInstanceId.getInstance().instanceId
               .addOnCompleteListener(OnCompleteListener { task ->
                   if (!task.isSuccessful) {
                       //  Log.w(TAG, "getInstanceId failed", task.exception)
                       return@OnCompleteListener
                   }
                   // Get new Instance ID token
                   val token = task.result?.token
                   Constants.getPrefs(this@SplashActivity)!!.edit().putString(Constants.DEVICE_ID, token).apply()
               })
*/

        loggedIn = Constants.getPrefs(this)!!.getBoolean(Constants.loggedIn, false)
      //  val language = Constants.getPrefs(this)!!.getString(Constants.LANGUAGE, "English")
       // val country = Constants.getPrefs(this)!!.getString(Constants.COUNTRY, "en")
      //  if (!language.isNullOrEmpty() && !country.isNullOrEmpty()) {
      //      setEnglishLanguage(language, country)
     //   }
        val t = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                    if (loggedIn) {
                        val intent = Intent(this@SplashActivity, DashBoardActivity::class.java)
                        startActivity(intent)
                    } else {
                       // startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                        startActivity(Intent(this@SplashActivity,LoginActivity::class.java))
                    }
                    finish()
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    finish()
                }
            }
        }
        t.start()
//        languageSpinner. visibility = View.INVISIBLE
//        languageSpinner.adapter = MoodArrayAdapter( this, listOf( Mood(0, "Selct Any Language"),Mood(R.drawable.flag_one, "English"),Mood(R.drawable.flag_two, "Brazil")
//        ))
//
//        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                if (position ==0) {
//                    SelectedLanguage="pt"
//
//                    setLocale("pt")
//                    Log.e("SelectedLanguage","SelectedLanguage" +SelectedLanguage)
//                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                }
//
//            }
//        }
    }






//    fun setEnglishLanguage(language: String, country: String) {
//        val myLocale = Locale(language, country)
//        val res = resources
//        val dm = res.displayMetrics
//        val conf = res.configuration
//        conf.locale = myLocale
//        res.updateConfiguration(conf, dm)
//    }
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
