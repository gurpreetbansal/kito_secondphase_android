package com.diggytech.kinoplasticpremios.RedeemPrizes

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

class RedeemPrizesContract {
    interface View {
        fun setAdapter(list: MutableList<ModelRedeemPrize>)
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)

    }

    interface Service {

        @GET("voucher/getVoucherList")
        fun callGetVouchersList(): Call<ResponseBody>

    }

}
