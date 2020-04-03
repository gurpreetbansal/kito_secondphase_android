package com.diggytech.kinoplasticpremios.Settings

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class SettingsContract {
    interface View {
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)

        fun setAdapter(list: MutableList<ModelSettings>)
        fun goToHomescreen()

    }

    interface Service {
        @GET("auth/logout")
        fun getProfileService(): Call<ResponseBody>

        @GET("auth/terminateAccount")
        fun terminateAccount(): Call<ResponseBody>
    }
}
