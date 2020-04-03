package com.diggytech.kinoplasticpremios.MySpace

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class MySpaceContract {
    interface View {
        fun setPointsEarntAdapter(list: MutableList<ModelPointsEarnt>)
        fun setPointsSpentAdapter(list: MutableList<ModelPointsSpent>)
        fun setUserDetails()
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(error: String)
        fun showErrorMessage(message: String?)
        fun setPoints(availablePoints: String?, pendingPoints: String?)
    }

    interface Service {


        @GET("mySpace")
        fun getMySpace(): Call<ResponseBody>

    }
}