package com.diggytech.kinoplasticpremios.Settings.ChangePassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_changepassword.*

class ChangepasswordActivity : AppCompatActivity(), ChangePasswordContract.View {

    private lateinit var mPresenter: ChangePasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)
        setToolBar()
        mPresenter = ChangePasswordPresenter(this)

        btnUpdate.setOnClickListener {
            Constants.onHideKeyboard(this)
            mPresenter.changePasswordService()
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.change_password)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun getOldPass(): String {
        return etOldPassword.text.toString().trim()
    }

    override fun getNewPass(): String {
        return etNewPassword.text.toString().trim()
    }

    override fun getConfirmPass(): String {
        return etConfirmPassword.text.toString().trim()
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@ChangepasswordActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@ChangepasswordActivity)
    }

    override fun showError(message: String) {
        Constants.showAlert(this@ChangepasswordActivity, message)
    }

    override fun showErrorMessage(message: String) {
        Constants.showAlert(this@ChangepasswordActivity, message)
    }

    override fun showSuccessMessage(message: String) {
        Constants.showFinishingAlert(this@ChangepasswordActivity, message)
    }

}
