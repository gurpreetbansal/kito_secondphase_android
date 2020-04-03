package com.diggytech.kinoplasticpremios.Profile.ViewProfile

import com.diggytech.kinoplasticpremios.Constants
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ViewProfilePresenter(val view: ViewProfileContract.View) {
    fun getProfileData() {
        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()

        view.showLoader()

        val client = Constants.getHttpClient(userId, AuthorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: ViewProfileContract.Service = retrofit.create(ViewProfileContract.Service::class.java)
        val call: Call<ResponseBody> = service.getProfileService()
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
                            val data = object1.optJSONObject("data")
                            view.setProfileData(parseResponse(data))
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

    private fun parseResponse(data: JSONObject): ModelProfile {
        val model = ModelProfile()
        model.id = data.optString("id")
        model.username = data.optString("username")
        model.email = data.optString("email")
        model.cpf_number = data.optString("cpf_number")
        model.phone_number = data.optString("phone_number")
        model.address = data.optString("address")
        model.brand = data.optString("brand")
        model.profile_pic = data.optString("profile_pic")
        model.state = data.optString("state")
        model.city = data.optString("city")
        model.location = data.optString("address_field")
        return model
    }

}
