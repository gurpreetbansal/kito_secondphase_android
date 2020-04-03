package com.diggytech.kinoplasticpremios.Support

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.Settings.ChangePassword.ChangePasswordContract
import com.diggytech.kinoplasticpremios.Settings.ChangePassword.ChangePasswordPresenter
import kotlinx.android.synthetic.main.activity_changepassword.*
import kotlinx.android.synthetic.main.activity_changepassword.progressBar
import kotlinx.android.synthetic.main.fragment_support.*
import kotlinx.android.synthetic.main.fragment_support.view.*

class SupportActivity : AppCompatActivity(), SupportContract.View {

    private lateinit var mPresenter: SupportPresenter
    var support_id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_support)
        setToolBar()
        mPresenter = SupportPresenter(this)
        mPresenter.getQuestionsList()

        val tvCancel = findViewById<TextView>(R.id.tvCancel)
        val etComment = findViewById<EditText>(R.id.etComment)
        val btnSubmit = findViewById<TextView>(R.id.btnSubmit)

        if (this != null ) {
            this!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        tvCancel.setOnClickListener {
            etComment.setText("")
            etComment.clearFocus()
        }
       btnSubmit.setOnClickListener {
            mPresenter.setSupportApi()
        }


    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.support)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@SupportActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@SupportActivity)
    }

    override fun showError(message: String) {
        Constants.showAlert(this@SupportActivity, message)
    }

    override fun showSuccessMessage(message: String?) {
        Constants.showFinishingAlert(this@SupportActivity, message!!)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this@SupportActivity, message!!)
    }

    override fun setQuestions(titles: MutableList<LocationsModel>) {
        if (this != null) {
            Constants.callSpinner(this!!, spinner, titles, 0)

           spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    support_id = titles[position].id

                }
            }
        }
    }

    override fun getSupportId(): String {
        return support_id
    }

    override fun getRemarks(): String {
        return etComment.text.toString().trim()
    }
}

