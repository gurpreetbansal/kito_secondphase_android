package com.diggytech.kinoplasticpremios.Newsletter

import android.os.Handler
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpContract
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ChlidDataModel
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ParentDataModel
import com.diggytech.kinoplasticpremios.R
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class NewsLetterPresenter(val view: NewsLetterContract.View) {
    var selection_multiple_locations = ArrayList<ChildNewsDataModel>();
    private var call: Call<ResponseBody>? = null

    var parent_model_signup = ParentNewsdataModel()


    fun callgetVouchersApi() {
        val locationId = view.getLocationId()
        val brand_name = view.getBrandName()
        val userId = view.getUserId()
        val userType = view.getUserType()
        val brand_two = view.getBrandName_All()
        val authorizationToken = view.getAuthToken()

        Log.e("BRAMNSS", "BRAMNSS" + brand_two)


        view.showLoader()


        if (userType.equals("1")) {
            val client = Constants.getHttpClient(userId, authorizationToken)
            val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
            val service: NewsLetterContract.Service =
                retrofit.create(NewsLetterContract.Service::class.java)
            val call: Call<ResponseBody> = service.callGetNotificationList(locationId, brand_name)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    view.hideLoader()
                    view.showError("No internet connection")
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    view.hideLoader()
                    if (response.isSuccessful)
                    {
                        val res = response.body()!!.string()
                        try
                        {
                            val object1 = JSONObject(res)
                            // val success = object1.optString("success")
                            val code = object1.optInt("code")
                            val message = object1.optString("message")
                            if (code == 200) {
                                val data = object1.optJSONArray("data")

                                if (data.length() > 0) {
                                    view.showSuccessMessage(message)
                                    view.setAdapter(parseResponse(data))
                                } else {
                                    // view.showEmptyListText()
                                    view.showErrorMessage(message)
                                }

                            } else {
                                view.showErrorMessage(message)
                            }
                        }
                        catch (e: Exception)
                        {
                            e.printStackTrace()
                        }
                    }
                    else
                    {
                        view.showError("Server Error")
                    }
                    try
                    {
                        client.dispatcher().executorService().shutdown()
                        client.cache()!!.delete()
                    }
                    catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            })
        } else {
            Handler().postDelayed({
                // This method will be executed once the timer is over
// Start your app main activity
                getDetailsOfScreen2_UserType()
// close this activity

            }, 5000)

        }


    }


    /*for user type two*/
    fun getDetailsOfScreen2_UserType() {

        val user_Type = view.getUserType()


        val getLocationArray = view.getLocationArray(ArrayList<ChildNewsDataModel>())

        for (i in 0 until getLocationArray.size) {
            var model = ChildNewsDataModel()
            model.brand = getLocationArray[i].brand
            model.locationID = getLocationArray.get(i).locationID
            selection_multiple_locations.add(model);
        }
        parent_model_signup.user_type = user_Type
        parent_model_signup.location_brand=selection_multiple_locations
        callSignUpServiceTwo()
    }


    /*in case of user two and three*/
    private fun callSignUpServiceTwo() {
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service: NewsLetterContract.Service = retrofit.create(NewsLetterContract.Service::class.java)
        call = service.newsTwo(parent_model_signup)
        Log.e("SECOND_SECTION", call.toString())

        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful)
                {
                    val res = response.body()!!.string()
                    try
                    {
                        val object1 = JSONObject(res)
                        // val success = object1.optString("success")
                        val code = object1.optInt("code")
                        val message = object1.optString("message")
                        if (code == 200)
                        {
                            val data = object1.optJSONArray("data")

                            if (data.length() > 0) {

                                view.setAdapter(parseResponse(data))
                            }
//                            else
//                            {
//                                //view.showSuccessMessage(message)
//                                 //view.showEmptyListText()
//                               // view.showErrorMessage(message)
//                            }

                        }
//                        else
//                        {
//                           view.showErrorMessage(message)
//                        }
                    }
                    catch (e: Exception)
                    {
                        e.printStackTrace()
                    }
                }
                else
                {
                    view.showError("Server Error")
                }
//                try
//                {
//                    client.dispatcher().executorService().shutdown()
//                    client.cache()!!.delete()
//                }
//                catch (e: java.lang.Exception) {
//                    e.printStackTrace()
//                }
            }
        })


    }



    private fun parseResponse(data: JSONArray?): MutableList<ModelNewsLetter> {
        val list = mutableListOf<ModelNewsLetter>()

        for (i in 0 until data!!.length()) {
            val object1 = data.optJSONObject(i)
            val model = ModelNewsLetter()
            model.id = object1.optString("id")
            model.cover_pic = object1.optString("cover_pic")
            model.title = object1.optString("title")
            model.message = object1.optString("message")
            model.created_at = object1.optString("created_at")
            model.brand = object1.optString("brand")
            model.location = object1.optString("location")
            list.add(model)
        }

        return list
    }

}
