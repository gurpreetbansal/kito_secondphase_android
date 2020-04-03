package com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.RedeemPrizes.ModelRedeemPrize
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class GiftCardPresenter(val view: GiftcardContract.View) {
    fun setData(model: ModelRedeemPrize?) {
        if (model != null) {
            view.setGiftdata(model)
        }
    }

    fun redeemPoints() {
        val voucher_id = view.getVoucherId()
        val requested_points = view.getRequestedPoints()
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()


        if (requested_points.trim().isEmpty()) {
            view.showError("Please choose value for redemption.")
            return
        }

        redemptionApi(voucher_id, requested_points, userId, authorizationToken)

    }

    private fun redemptionApi(voucherId: String, requestedPoints: String, userId: String, authorizationToken: String) {
        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)

        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: GiftcardContract.Service = retrofit.create(GiftcardContract.Service::class.java)
        val call: Call<ResponseBody> = service.callRedeemVoucher(voucherId, requestedPoints)
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
                            view.showSuccessMessage()
                        } else {
                            view.showErrorMessage(message)
                        }
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