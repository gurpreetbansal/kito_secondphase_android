package com.diggytech.kinoplasticpremios.Campaign.Products

import com.diggytech.kinoplasticpremios.Campaign.ModelCampaign
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class ProductsContract {
    interface View {
        fun setAdapter(list: MutableList<ModelProducts>)
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)
        fun setCampaignData(modelCampaign: ModelCampaign)

    }

    interface Service {

        @FormUrlEncoded
        @POST("campaign/getCampaignDetail")
        fun getCampaignProducts(@Field("campaign_id") id: String): Call<ResponseBody>

    }

}