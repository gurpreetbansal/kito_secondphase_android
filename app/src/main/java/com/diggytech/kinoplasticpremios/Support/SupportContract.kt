package com.diggytech.kinoplasticpremios.Support

import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

class SupportContract {
    interface View {
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showSuccessMessage(message: String?)
        fun showErrorMessage(message: String?)
        fun setQuestions(titles: MutableList<LocationsModel>)
        fun getSupportId(): String
        fun getRemarks(): String

    }

    interface Service {

        @FormUrlEncoded
        @POST("support/generateTicket")
        fun callSupportService(@Field("support_id") supportId: String,@Field("remarks") remarks: String): Call<ResponseBody>

        @GET("support/getTitle")
        fun getSupportquestions(): Call<ResponseBody>

    }

}
