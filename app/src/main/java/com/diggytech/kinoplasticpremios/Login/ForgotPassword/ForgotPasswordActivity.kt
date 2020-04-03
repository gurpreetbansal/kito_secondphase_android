package com.diggytech.kinoplasticpremios.Login.ForgotPassword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity(), ForgotPasswordContract.View {

    private lateinit var mPresenter: ForgotPasswordPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        mPresenter = ForgotPasswordPresenter(this)

        tvClose.setOnClickListener {
            Constants.onHideKeyboard(this)
            finish()
        }

        btnSignIn.setOnClickListener {
            mPresenter.callForgotPasswordService(this)
        }
    }


    override fun getEmail(): String {
        return etName.text.toString().trim()
    }

    override fun showError(message: String) {
        Constants.showAlert(this@ForgotPasswordActivity, message)
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this@ForgotPasswordActivity)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this@ForgotPasswordActivity)
    }

    override fun showErrorMessage(error: String) {
        Constants.showAlert(this@ForgotPasswordActivity, error)
    }

    override fun showSuccessMessage(success: String) {
        Constants.showFinishingAlert(this@ForgotPasswordActivity, success)
    }
}
