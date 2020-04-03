package com.diggytech.kinoplasticpremios.MySpace

import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MySpacePresenter(val view: MySpaceContract.View) {
    fun getUserDetails() {
        view.setUserDetails()
    }

    fun getMySpacedata() {
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()

        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: MySpaceContract.Service = retrofit.create(MySpaceContract.Service::class.java)
        val call: Call<ResponseBody> = service.getMySpace()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {
                view.hideLoader()
                if (response.isSuccessful)
                {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val message = object1.optString("message")
                        if (success == "true") {
                            val data = object1.optJSONObject("data")
                            val available_points = data.optString("available_points")
                            val pending_points = data.optString("pending_points")
                            val pointsEarned = data.optJSONArray("pointsEarned")
                            val pontSpent = data.optJSONArray("pontSpent")
                            view.setPoints(available_points, pending_points)
                            view.setPointsEarntAdapter(parseEarnedResponse(pointsEarned))
                            view.setPointsSpentAdapter(parseSpentResponse(pontSpent))
                        } else {
                            view.showErrorMessage(message)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                else
                {

                    //view.showErrorMessage(message)
                    view.showError("No internet connection")
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

    private fun parseSpentResponse(pontSpent: JSONArray?): MutableList<ModelPointsSpent> {
        val list = mutableListOf<ModelPointsSpent>()
        for (i in 0 until pontSpent!!.length()) {
            val object1 = pontSpent.optJSONObject(i)
            val modelPointsSpent = ModelPointsSpent()
            modelPointsSpent.points = object1.optString("points")
            modelPointsSpent.redemption_date = object1.optString("redeemption_date")
            modelPointsSpent.gift_card = object1.optString("gift_card")
            modelPointsSpent.cost = object1.optString("cost")
            modelPointsSpent.id = object1.optString("id")
            list.add(modelPointsSpent)
        }

        return list
    }

    private fun parseEarnedResponse(pointsEarned: JSONArray?): MutableList<ModelPointsEarnt> {
        val list = mutableListOf<ModelPointsEarnt>()
        for (i in 0 until pointsEarned!!.length()) {
            val object1 = pointsEarned.optJSONObject(i)
            val modelPointsEarnt = ModelPointsEarnt()
            modelPointsEarnt.id = object1.optString("id")
            modelPointsEarnt.points = object1.optString("points")
            modelPointsEarnt.claimed_on = object1.optString("claimed_on")
            modelPointsEarnt.expires_on = object1.optString("expires_on")
            modelPointsEarnt.status = object1.optString("status")
            list.add(modelPointsEarnt)
        }
        return list
    }

}