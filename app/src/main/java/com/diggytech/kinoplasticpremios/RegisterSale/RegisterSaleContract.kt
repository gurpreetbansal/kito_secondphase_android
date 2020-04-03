package com.diggytech.kinoplasticpremios.RegisterSale

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class RegisterSaleContract {
    interface View {
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)
        fun getQrCode(): String
        fun getCampaignId(): String
        fun getAddress(): String
        fun showSuccessMessage(message: String?)

    }

    interface Service {

        @FormUrlEncoded
        @POST("campaign/claimCampaignPoints")
        fun claimCampaignPoints(@Field("qr_code") qr_code: String, @Field("campaign_id") campaign_id: String,@Field("location") address_id: String): Call<ResponseBody>
//fun claimCampaignPoints(@Field("qr_code") qr_code: String, @Field("campaign_id") campaign_id: String, @Field("AuthorizationToken") authorization_token: String,@Field("userId") user_id: String): Call<ResponseBody>
    }

}
