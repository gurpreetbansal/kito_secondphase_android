package com.diggytech.kinoplasticpremios.SelectLocation

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SelectLocationPresenter(val view: SelectLocationContract.View) {
    fun getCampaigns() {
        val location = view.getLocationId()
        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()

        getCampaigns(location, userId, AuthorizationToken)
    }

    private fun getCampaigns(location: String, userId: String, authorizationToken: String) {
        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: SelectLocationContract.Service = retrofit.create(SelectLocationContract.Service::class.java)
        val call: Call<ResponseBody> = service.getCampaignsService(location)
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
                            view.setSpinner(parseResponse(data))
                        }
                        else {
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

    private fun parseResponse(data: JSONArray): MutableList<LocationsModel> {

        val list = mutableListOf<LocationsModel>()
        for (i in 0 until data.length()) {
            val model = LocationsModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optString("id")
            model.name = object1.optString("name")
            list.add(model)
        }

        return list
    }

}
