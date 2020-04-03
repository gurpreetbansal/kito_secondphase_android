package com.diggytech.kinoplasticpremios.Campaign.Products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Campaign.ModelCampaign
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_products.*

class ProductsActivity : AppCompatActivity(), ProductsContract.View {

    private lateinit var list1: MutableList<ModelProducts>
    private lateinit var productsAdapter: ProductsAdapter
    private lateinit var mPresenter: ProductsPresenter
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        mPresenter = ProductsPresenter(this)

        try {
            id = intent.getStringExtra("id")
            mPresenter.getProductsOfCampaigns(id)
        } catch (e: Exception) {

        }

        setToolBar()


        grid.setOnClickListener {
            list.setImageResource(R.drawable.listview_white)
            grid.setImageResource(R.drawable.gridview)
            recyclerview.layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                this,
                2,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            productsAdapter = ProductsAdapter(this, "2", list1)
            recyclerview.adapter = productsAdapter
        }
        list.setOnClickListener {
            list.setImageResource(R.drawable.listview)
            grid.setImageResource(R.drawable.gridview_white)
            recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            productsAdapter = ProductsAdapter(this, "1", list1)
            recyclerview.adapter = productsAdapter

        }
    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.products)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setAdapter(list: MutableList<ModelProducts>) {
        productsText.text = "${list.size} Products"
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
            this,
            androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
            false
        )
        list1 = list
        productsAdapter = ProductsAdapter(this@ProductsActivity, "1", list1)
        recyclerview.adapter = productsAdapter

    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this)
    }

    override fun showError(message: String) {
        Constants.showAlert(this, message)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this, message!!)
    }

    override fun setCampaignData(modelCampaign: ModelCampaign) {
        tvHeading.text = modelCampaign.name
        tvDesc.text = modelCampaign.description

    }
}
