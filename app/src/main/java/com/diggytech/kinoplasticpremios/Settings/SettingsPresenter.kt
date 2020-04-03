package com.diggytech.kinoplasticpremios.Settings

import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SettingsPresenter(val view: SettingsContract.View) {
    fun setDataToRecycler(context: SettingsActivity) {
        val list = mutableListOf<ModelSettings>()
        var model = ModelSettings()
        model.title = context.getString(R.string.change_password)
        model.img = R.drawable.change_password
        list.add(model)

        model = ModelSettings()
        model.title = context.getString(R.string.terms_conditions)
        model.img = R.drawable.terms_conditions
        list.add(model)

        model = ModelSettings()
        model.title = context.getString(R.string.privacy_policy)
        model.img = R.drawable.privacy_policy
        list.add(model)

        model = ModelSettings()
        model.title = context.getString(R.string.delete_account)
        model.img = R.drawable.ic_delete2
        list.add(model)

        model = ModelSettings()
        model.title = context.getString(R.string.support)
        model.img = R.drawable.support_two
        list.add(model)


        view.setAdapter(list)
    }

    fun callLogoutService() {

        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()

        view.showLoader()

        val client = Constants.getHttpClient(userId, AuthorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: SettingsContract.Service = retrofit.create(SettingsContract.Service::class.java)
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
                            view.goToHomescreen()

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
