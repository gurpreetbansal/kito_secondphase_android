package com.diggytech.kinoplasticpremios.Notification

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_notification.*

class NotificationActivity : AppCompatActivity(), NotificationContract.View {

    private lateinit var mPresenter: NotificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        mPresenter = NotificationPresenter(this)
        setToolBar()
        mPresenter.setDataToRecycler()
    }


    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.notifications)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setAdapter(list: MutableList<ModelNotification>) {
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        val adapter = NotificationAdapter(this, list)
        recyclerview.adapter = adapter
    }
}
