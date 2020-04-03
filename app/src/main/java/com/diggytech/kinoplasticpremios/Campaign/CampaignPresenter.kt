package com.diggytech.kinoplasticpremios.Campaign

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class CampaignPresenter(val view: CampaignContract.View) {
	fun callCampaignApi() {
		val location = view.getLocationId()
		val location_two = view.getLocationId_Two()
		val userId = view.getUserId()
		val userType = view.getUserType()
		val AuthorizationToken = view.getAuthToken()

//if(userType.equals("1"))
//{
	getCampaigns(location, userId, AuthorizationToken)
//}
//	else {
//	getCampaigns(location_two, userId, AuthorizationToken)
//}
	}

	private fun getCampaigns(location: String, userId: String, authorizationToken: String) {
		view.showLoader()

		val client = Constants.getHttpClient(userId, authorizationToken)
		val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
		val service: CampaignContract.Service = retrofit.create(CampaignContract.Service::class.java)
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


	private fun parseResponse(data: JSONArray): MutableList<ModelCampaign> {

		val list = mutableListOf<ModelCampaign>()
		for (i in 0 until data.length()) {
			val model = ModelCampaign()
			val object1 = data.optJSONObject(i)
			model.id = object1.optString("id")
			model.name = object1.optString("name")
			model.image = object1.optString("image")
			model.start_date = object1.optString("start_date")
			model.end_date = object1.optString("end_date")
			list.add(model)
		}

		return list
	}

}