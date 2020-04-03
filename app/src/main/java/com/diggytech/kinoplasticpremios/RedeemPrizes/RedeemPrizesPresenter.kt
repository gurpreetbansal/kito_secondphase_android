package com.diggytech.kinoplasticpremios.RedeemPrizes

import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RedeemPrizesPresenter(val view: RedeemPrizesContract.View) {

    fun callgetVouchersApi() {
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()

        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)

        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: RedeemPrizesContract.Service = retrofit.create(RedeemPrizesContract.Service::class.java)
        val call: Call<ResponseBody> = service.callGetVouchersList()
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
                            val data = object1.optJSONArray("data")
                            view.setAdapter(parseResponse(data))
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

    private fun parseResponse(data: JSONArray?): MutableList<ModelRedeemPrize> {
        val list = mutableListOf<ModelRedeemPrize>()

        for (i in 0 until data!!.length()) {
            val object1 = data.optJSONObject(i)
            val model = ModelRedeemPrize()
            model.id = object1.optString("id")
            model.image = object1.optString("image")
            model.title = object1.optString("title")
            model.price_range = object1.optString("price_range")
            model.validity_period = object1.optString("validity_period")
            model.description = object1.optString("description")
            model.points_consumed = object1.optString("points_consumed")
            list.add(model)
        }

        return list
    }

}
