package com.diggytech.kinoplasticpremios.Support

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SupportPresenter(val view: SupportContract.View) {
    fun setSupportApi() {
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()
        val support_id = view.getSupportId()
        val remarks = view.getRemarks()

        if (remarks.trim().isEmpty()) {
            view.showError("Please enter your remarks.")
            return
        }

        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)

        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: SupportContract.Service = retrofit.create(SupportContract.Service::class.java)
        val call: Call<ResponseBody> = service.callSupportService(support_id, remarks)
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

    fun getQuestionsList() {
        val userId = view.getUserId()
        val authorizationToken = view.getAuthToken()


        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)

        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: SupportContract.Service = retrofit.create(SupportContract.Service::class.java)
        val call: Call<ResponseBody> = service.getSupportquestions()
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
                            view.setQuestions(parseTitles(data))
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

    private fun parseTitles(data: JSONArray?): MutableList<LocationsModel> {
        val list = mutableListOf<LocationsModel>()
        for (i in 0 until data!!.length()) {
            val model = LocationsModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optString("id")
            model.name = object1.optString("title")
            list.add(model)
        }
        return list
    }

}
