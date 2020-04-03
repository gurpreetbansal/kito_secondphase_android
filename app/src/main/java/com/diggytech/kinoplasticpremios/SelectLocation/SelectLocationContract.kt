package com.diggytech.kinoplasticpremios.SelectLocation

import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SelectLocationContract {
    interface View {
        fun setSpinner(list: MutableList<LocationsModel>)
        fun getLocationId(): String
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(error: String)
        fun showErrorMessage(message: String?)
    }

    interface Service {
        @FormUrlEncoded
        @POST("campaign/getLocationCampaigns")
        fun getCampaignsService(@Field("location") location: String): Call<ResponseBody>
    }
}
