package com.diggytech.kinoplasticpremios.SelectLocation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RegisterSale.RegisterSaleActivity
import kotlinx.android.synthetic.main.activity_select_location.*

class SelectLocationActivity : AppCompatActivity(), SelectLocationContract.View {

    private lateinit var mPresenter: SelectLocationPresenter
    var campaignId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)
        setToolBar()
        mPresenter = SelectLocationPresenter(this)
        mPresenter.getCampaigns()
    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.select_campaign)
    }

    override fun setSpinner(list: MutableList<LocationsModel>) {
        try {

            Constants.callSpinner(this@SelectLocationActivity, spinnerLocations, list, 0)

            spinnerLocations.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    campaignId = list[spinnerLocations.selectedItemPosition].id

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        btnSubmit.setOnClickListener {
            val intent = Intent(this@SelectLocationActivity, RegisterSaleActivity::class.java)
            intent.putExtra("id", campaignId)
            startActivity(intent)
        }

    }

    override fun getLocationId(): String {
        return Constants.getPrefs(this@SelectLocationActivity)!!.getString(Constants.ADDRESS, "")!!
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this@SelectLocationActivity)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this@SelectLocationActivity)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@SelectLocationActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@SelectLocationActivity)
    }

    override fun showError(error: String) {
        Constants.showAlert(this@SelectLocationActivity, error)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this@SelectLocationActivity, message!!)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
