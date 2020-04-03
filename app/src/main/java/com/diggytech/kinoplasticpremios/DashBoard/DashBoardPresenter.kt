package com.diggytech.kinoplasticpremios.DashBoard

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class DashBoardPresenter(val view: DashBoardContract.View) {
    fun setUpBottomNavigationLayout() {
        view.setUpBottomNavigation()
    }

    fun getAllLocations() {
       // view.showLoader()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: DashBoardContract.Service = retrofit.create(DashBoardContract.Service::class.java)
        val call: Call<ResponseBody> = service.getLocations()
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
                            view.saveLocations(parseLocations(data))
                        } else {
                            view.showErrorMessage(message)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    view.showError("Server Error")
                }
            }
        })
    }

    private fun parseLocations(data: JSONArray): MutableList<LocationsModel> {
        val list = mutableListOf<LocationsModel>()
        for (i in 0 until data.length()) {
            val model = LocationsModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optString("id")
            model.name = object1.optString("name")
            model.state = object1.optString("state")
            model.city = object1.optString("city")
            val brandArry = object1.optJSONArray("brand")
            model.brand = brandArry.optString(0)
            list.add(model)
        }
        return list

    }

}