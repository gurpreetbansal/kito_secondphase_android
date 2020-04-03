package com.diggytech.kinoplasticpremios.Login.SignIn

import android.content.Intent
import android.os.Parcelable
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards.GiftCardsActivity
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class SignInPresenter(val view: SignInContract.View) {

    fun callLoginService(context: FragmentActivity?) {
        val email = view.getEmail()
        val password = view.getPassword()
        val device_type = view.getDeviceType()
        val device_token = view.getDeviceToken()
        val fcm_token = view.gefcmtoken()

        if (email.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_email_or_cpf))
            return
        }

        if(password.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_password))
            return
        }
        /* if (!Constants.isEmailValid(email)) {
             view.showError("Please Enter Valid Email")
             return
         }*/

        callLoginApi(email, password, device_type, device_token,fcm_token)
    }

    private fun callLoginApi(email: String, password: String, deviceType: String, deviceToken: String,fcm_token:String) {
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignInContract.Service = retrofit.create(SignInContract.Service::class.java)
        val call: Call<ResponseBody> = service.callLoginService(email, password, deviceType, deviceToken,fcm_token)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
               // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true") {
                            val data = object1.optJSONObject("data")
                            view.saveLoginDataToPrefs(data, token)
                        } else {
                            val message = object1.optString("message")
                            view.showErrorMessage(message)//111
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

    fun setUpFacebookSignUp() {
        view.setFacebookSignUp()
    }


/*{'email': 'testuser@gmail.com','social_type' : 'facebook','social_id':'eqrwrere','device_type':'android','device_token':'243242df43234' }*/
     fun callfacebookLoginApi(email: String, social_type: String, social_id:String,deviceType: String, deviceToken: String,state:String,city:String) {
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignInContract.Service = retrofit.create(SignInContract.Service::class.java)
        val call: Call<ResponseBody> = service.callfacebookLoginService(email, social_type,social_id, deviceType, deviceToken,state,city)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true") {
                            val data = object1.optJSONObject("data")
                            view.saveLoginDataToPrefs(data, token)
                        } else {
                            val message = object1.optString("message")
                            view.showErrorMessage(message)//111
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



    fun callSignUpService(
        deviceType: String,
        deviceToken: String,
        username: String,
        cpf: String,
        contact: String,
        location: String,
        brand: String,
        email: String,
        password: String,
        socialType: String,
        socialId: String
    ) {

        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignInContract.Service = retrofit.create(SignInContract.Service::class.java)

        val call: Call<ResponseBody> = service.callRegisterService(
            deviceType,
            deviceToken,
            username,
            cpf,
            contact,
            location,
            brand,
            email,
            password,
            socialType,
            socialId
        )
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
              //  view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true") {
                            val data = object1.optJSONObject("data")
                            view.saveLoginDataToPrefs(data, token)



                        } else {
                            val message = object1.optString("message")
                            view.showErrorMessage(message)//222
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

    fun setUpGoogleSignUp() {
        view.setGoogleSignUp()
    }


    fun getAllLocations() {
        view.showLoader()

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignInContract.Service = retrofit.create(SignInContract.Service::class.java)
        val call: Call<ResponseBody> = service.getLocations()
        call.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
               // view.showError("No internet connection")
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
                            view.showErrorMessage(message)///33333
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
