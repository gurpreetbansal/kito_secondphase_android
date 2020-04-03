package com.diggytech.kinoplasticpremios.Settings

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.DashBoard.DashBoardActivity
import com.diggytech.kinoplasticpremios.Login.LoginActivity
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_settings.*
import java.util.*


class SettingsActivity : AppCompatActivity(), SettingsContract.View {
    private lateinit var mPresenter: SettingsPresenter

    var language = ""
    var country = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setToolBar()
        mPresenter = SettingsPresenter(this)
        mPresenter.setDataToRecycler(this)

        ivLogout.setOnClickListener {
            showLogoutDialog()
        }

        val list = ArrayList<String>()

        list.add("Select language")
        list.add("English")
        list.add("Portuguese")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, l: Long) {
                when (position) {
                    0 -> {
                    }
                    1 -> {
                        language = "English"
                        country = "en"
                        setEnglishLanguage(language, country)
                        Constants.getPrefs(this@SettingsActivity)!!.edit().putString(Constants.LANGUAGE, language)
                            .apply()
                        Constants.getPrefs(this@SettingsActivity)!!.edit().putString(Constants.COUNTRY, country)
                            .apply()
                    }
                    2 -> {
                        language = "pt"
                        country = "BR"
                        setPortugueseLanguage(language, country)
                        Constants.getPrefs(this@SettingsActivity)!!.edit().putString(Constants.LANGUAGE, language)
                            .apply()
                        Constants.getPrefs(this@SettingsActivity)!!.edit().putString(Constants.COUNTRY, country)
                            .apply()
                    }

                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

    }

    private fun setPortugueseLanguage(language: String, country: String) {
        val myLocale = Locale(language, country)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, DashBoardActivity::class.java)
        startActivity(refresh)
        finishAffinity()
    }


    fun setEnglishLanguage(language: String, country: String) {
        val myLocale = Locale(language, country)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        val refresh = Intent(this, DashBoardActivity::class.java)
        startActivity(refresh)
        finishAffinity()
    }


    private fun showLogoutDialog() {
        val bulder = AlertDialog.Builder(this@SettingsActivity)
        bulder.setMessage(getString(R.string.are_you_sure_you_want_to_logout))
            .setPositiveButton(R.string.ok) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
                mPresenter.callLogoutService()
            }.setNegativeButton(R.string.cancel) { dialogInterface: DialogInterface, i: Int ->
                dialogInterface.dismiss()
            }.create().show()
    }


    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.settings)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun setAdapter(list: MutableList<ModelSettings>) {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        val adapter = SettingsAdapter(this, list)
        recyclerview.adapter = adapter
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@SettingsActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@SettingsActivity)
    }

    override fun showError(message: String) {
        Constants.showAlert(this@SettingsActivity, message)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this@SettingsActivity, message!!)
    }

    override fun goToHomescreen() {
        Constants.getPrefs(this@SettingsActivity)!!.edit().clear().apply()
        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}
