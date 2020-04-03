package com.android.skephome.OwnerData.ChangePassword

import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ChangePasswordPresenter(val view: ChangePasswordContract.View) {
    fun changePasswordService() {
        val old_password = view.getOldPass()
        val new_password = view.getNewPass()
        val confirm_password = view.getConfirmPass()
        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()
        val userType = view.getUserType()

        if (old_password.trim().isEmpty()) {
            view.showError("Please Enter your current password")
            return
        }
        if (new_password.trim().isEmpty()) {
            view.showError("Please Enter your new password")
            return
        }
        if (confirm_password.trim().isEmpty()) {
            view.showError("Please Enter your confirm password")
            return
        }
        if (!confirm_password.trim().equals(new_password.trim())) {
            view.showError("Passwords do not match")
            return
        }


        changePassword(userId, AuthorizationToken, userType, old_password, new_password, confirm_password)
    }

    private fun changePassword(
        userId: String,
        authorizationToken: String,
        userType: String,
        old_password: String,
        new_password: String,
        confirm_password: String
    ) {
        view.showLoader()

       // val client = Constants.getHttpClient(userId, authorizationToken, userType)
        val client = Constants.getHttpClient(userId, authorizationToken)

        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: ChangePasswordContract.Service = retrofit.create(ChangePasswordContract.Service::class.java)
        val call: Call<ResponseBody> = service.callChangePasswordService(old_password, new_password, confirm_password)
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
                try {
                    client.dispatcher().executorService().shutdown()
                    client.cache()!!.delete()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }


            }
        })


    }
}
