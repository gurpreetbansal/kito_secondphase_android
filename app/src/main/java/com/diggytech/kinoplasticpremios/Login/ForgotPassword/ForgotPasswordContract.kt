package com.diggytech.kinoplasticpremios.Login.ForgotPassword

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class ForgotPasswordContract {
    interface View {
        fun getEmail(): String
        fun showError(message: String)
        fun showLoader()
        fun hideLoader()
        fun showErrorMessage(error: String)
        fun showSuccessMessage(success: String)
    }

    interface Service {
        @FormUrlEncoded
        @POST("auth/forgotPassword")
        fun callLoginService(
            @Field("email") email: String
        ): Call<ResponseBody>
    }

}
