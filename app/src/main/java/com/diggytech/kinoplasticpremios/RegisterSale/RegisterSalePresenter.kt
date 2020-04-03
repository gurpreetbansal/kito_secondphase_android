package com.diggytech.kinoplasticpremios.RegisterSale

import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RegisterSalePresenter(val view: RegisterSaleContract.View) {
    fun setRegisterSale() {
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()
        val qrCode = view.getQrCode()
        val campaignID = view.getCampaignId()
        val addressID = view.getAddress()

        if (qrCode.trim().isEmpty()) {
            view.showError("Please scan QR code or enter manually.")
            return
        }

        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: RegisterSaleContract.Service = retrofit.create(RegisterSaleContract.Service::class.java)
        val call: Call<ResponseBody> = service.claimCampaignPoints(qrCode, campaignID,addressID)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val message = object1.optString("message")
                        if (success == "true") {
                            view.showSuccessMessage(message)
                        } /*else {
                            view.showErrorMessage(message)
                        }*/
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    view.showError("Server Error")
                }
                try {
                    client.dispatcher().executorService().shutdown()
                    client.cache()!!.delete()
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }


            }
        })
    }

}
