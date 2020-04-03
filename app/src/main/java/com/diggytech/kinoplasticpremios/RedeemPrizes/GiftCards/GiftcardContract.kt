package com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards

import com.diggytech.kinoplasticpremios.RedeemPrizes.ModelRedeemPrize
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class GiftcardContract {
    interface View {
        fun setGiftdata(model: ModelRedeemPrize)
        fun getVoucherId(): String
        fun getRequestedPoints(): String
        fun showError(message: String)
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()

        fun showErrorMessage(message: String?)
        fun showSuccessMessage()

    }

    interface Service {

        @FormUrlEncoded
        @POST("voucher/redeem")
        fun callRedeemVoucher(@Field("voucher_id") voucherId: String, @Field("requested_points") requestedPoints: String): Call<ResponseBody>
    }

}