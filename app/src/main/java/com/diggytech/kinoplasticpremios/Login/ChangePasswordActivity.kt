/*
package com.android.skephome.OwnerData.ChangePassword

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.android.skephome.Constants
import com.android.skephome.R
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_dash_board.*

class ChangePasswordActivity : AppCompatActivity(),
    ChangePasswordContract.View {
    private var status: String? = ""
    private lateinit var mPresenter: ChangePasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        mPresenter = ChangePasswordPresenter(this)
        status = Constants.getPrefs(this)!!.getString(Constants.userStatus, "")
        home.setImageResource(R.drawable.ic_home_black)
        clickListeners()
    }

    private fun clickListeners() {
        if (status == "homeOwner") {
            btnUpdate.visibility = View.VISIBLE
            btnUpdateCleaner.visibility = View.GONE

        } else {
            btnUpdate.visibility = View.GONE
            btnUpdateCleaner.visibility = View.VISIBLE
        }

        btnUpdate.setOnClickListener {
            Constants.onHideKeyboard(this@ChangePasswordActivity)
            mPresenter.changePasswordService()
        }
        btnUpdateCleaner.setOnClickListener {
            Constants.onHideKeyboard(this@ChangePasswordActivity)
            mPresenter.changePasswordService()
        }
        home.setOnClickListener {
            Constants.onHideKeyboard(this@ChangePasswordActivity)
            finish()
        }
    }

    override fun getOldPass(): String {
        return oldPass.text.toString().trim()
    }

    override fun getNewPass(): String {
        return newPass.text.toString().trim()
    }

    override fun getConfirmPass(): String {
        return confirmPass.text.toString().trim()
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun getUserType(): String {
        return status!!
    }

    override fun showError(error: String) {
        Constants.showAlert(this@ChangePasswordActivity, error)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@ChangePasswordActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@ChangePasswordActivity)
    }

    override fun showSuccessMessage(message: String) {
        Constants.showFinishingAlert(this@ChangePasswordActivity, message)
    }

    override fun showErrorMessage(message: String) {
        Constants.showAlert(this@ChangePasswordActivity, message)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Constants.onHideKeyboard(this@ChangePasswordActivity)
    }
}
*/
