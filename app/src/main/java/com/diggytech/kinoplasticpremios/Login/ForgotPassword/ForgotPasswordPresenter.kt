package com.diggytech.kinoplasticpremios.Login.ForgotPassword

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ForgotPasswordPresenter(val view: ForgotPasswordContract.View) {
    fun callForgotPasswordService(context: ForgotPasswordActivity) {
        val email = view.getEmail()

        if (email.trim().isEmpty()) {
            view.showError(context.getString(R.string.please_enter_email))
            return
        }
        if (!Constants.isEmailValid(email)) {
            view.showError(context.getString(R.string.enter_valid_email))
            return
        }

        callForgotService(email)
    }

    private fun callForgotService(email: String) {
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: ForgotPasswordContract.Service = retrofit.create(ForgotPasswordContract.Service::class.java)
        val call: Call<ResponseBody> = service.callLoginService(email)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val message = object1.optString("message")
                        if (success == "true") {
                            view.showSuccessMessage(message)
                        } else {
                            view.showErrorMessage(message)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    view.showError("Server Error")
                }
            }
        })
    }

}
