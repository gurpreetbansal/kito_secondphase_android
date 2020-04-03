package com.diggytech.kinoplasticpremios.Profile.ViewProfile

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

class ViewProfileContract {
    interface View {
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)
        fun setProfileData(model: ModelProfile)
    }

    interface Service {
        @POST("auth/getProfile")
        fun getProfileService(): Call<ResponseBody>
    }

}