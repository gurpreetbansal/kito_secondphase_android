package com.diggytech.kinoplasticpremios.Profile.EditProfile

import android.util.Log
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpContract
import com.diggytech.kinoplasticpremios.Profile.ViewProfile.ModelProfile
import okhttp3.MediaType
import okhttp3.MultipartBody
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

class EditProfilePresenter(val view: EditProfileContract.View) {
    private var profile_pic: MultipartBody.Part? = null
    var call: Call<ResponseBody>? = null

    var selection_multiple_locations = ArrayList<EditProfileRequest_ChildDataModel>();
    val parts = ArrayList<RequestBody>()
    var parent_model_signup =EditProfileRequest_ParentDataModel()

    lateinit var userId:String
    lateinit var AuthorizationToken:String
    lateinit var user_type:String

    fun getProfileData() {
        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()

        view.showLoader()

        val client = Constants.getHttpClient(userId, AuthorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: EditProfileContract.Service = retrofit.create(EditProfileContract.Service::class.java)
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

        model.state = data.optString("state")
        model.city = data.optString("city")
        model.location = data.optString("address_field")

        model.profile_pic = data.optString("profile_pic")
        return model
    }


    private fun parseStateLocations(data: JSONArray): MutableList<LocationsModel> {
        val list = mutableListOf<LocationsModel>()
        for (i in 0 until data.length()) {
            val model = LocationsModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optString("id")
            model.name = object1.optString("name")
            model.state = object1.optString("state")
            model.city = object1.optString("city")
            // val brandArry = object1.optJSONArray("brand")
            model.brand = object1.optString("brand")

            list.add(model)
        }
        return list

    }


    fun sendEditedData() {
        var image: File? = null
        val userId = view.getUserId()
        val AuthorizationToken = view.getAuthToken()
        val username = view.getUsername()
        val cpf = view.getCpf()
        val contact = view.getContact()
        image = view.getImageFile()
        val brand = view.getBrand()
        val state = view.getState()
        val city = view.getCity()
        val location = view.getLocation()




        val user_type = view.getUserType()
        if (image == null) {
            callEditProfileApi(userId, AuthorizationToken, null, username, cpf, contact,
                location, brand, city, state,user_type)
        } else {
            callEditProfileApi(userId, AuthorizationToken, image, username, cpf, contact, location, brand, city, state,user_type)
        }

    }

    private fun callEditProfileApi(
        userId: String,
        authorizationToken: String,
        image: File?,
        username: String,
        cpf: String,
        contact: String,
        location: String,
        brand: String,
        city: String,
        state: String,
        user_type: String

    ) {
        view.showLoader()

        val client = Constants.getHttpClient(userId, authorizationToken)
        val retrofit = Retrofit.Builder().client(client).baseUrl(Constants.BASE_URL).build()
        val service: EditProfileContract.Service = retrofit.create(EditProfileContract.Service::class.java)

        if (image != null) {
            val requestFile = RequestBody.create(MediaType.parse("image*//*"), image)
            profile_pic = MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestFile)
        }

        val username1 = RequestBody.create(MultipartBody.FORM, username)
        val cpf1 = RequestBody.create(MultipartBody.FORM, cpf)
        val contact1 = RequestBody.create(MultipartBody.FORM, contact)
        val location1 = RequestBody.create(MultipartBody.FORM, location)
        val brand1 = RequestBody.create(MultipartBody.FORM, brand)
        val city1 = RequestBody.create(MultipartBody.FORM, city)
        val state1 = RequestBody.create(MultipartBody.FORM, state)
        val user_type = RequestBody.create(MultipartBody.FORM, user_type)
        if (image != null) {
            call = service.editProfileService(profile_pic, username1, cpf1, contact1, location1, brand1, city1, state1,user_type)
        } else {
            call = service.editProfileService(null, username1, cpf1, contact1, location1, brand1, city1, state1,user_type)
        }

        call!!.enqueue(object : Callback<ResponseBody> {
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
                            view.saveRegisterDataToPrefs(data)
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
/*for type two*/
fun sendEditedData_Two() {

     userId = view.getUserId()
    AuthorizationToken = view.getAuthToken()
    val username = view.getUsername()
    val cpf = view.getCpf()
    val contact = view.getContact()

    val selectedUserType = view.getUserType()
    val getLocationArray = view.getLocationArray(ArrayList<EditProfileRequest_ChildDataModel>())
    for (i in 0 until getLocationArray.size) {
        var model = EditProfileRequest_ChildDataModel()
        model.brand = getLocationArray[i].brand
        model.location = getLocationArray.get(i).location
        model.city = getLocationArray.get(i).city
        model.state = getLocationArray.get(i).state
        model.locationID = getLocationArray.get(i).locationID
        selection_multiple_locations.add(model);
    }


    parent_model_signup.cpf_number=cpf
    parent_model_signup.phone_number=contact
    parent_model_signup.profile_pic= ""
    parent_model_signup.user_type= selectedUserType.toString()
    parent_model_signup.username=username
    parent_model_signup.user_Location=selection_multiple_locations




    if ( selectedUserType.equals("2")||selectedUserType.equals("3") )
    {
        Log.e("SENDING_DATA","SENDING_DATA"+parent_model_signup)
        callSignUpServiceTwo()
    }
}

    /*in case of user two and three*/
    private fun callSignUpServiceTwo()
    {
        val image = view.getImageFile()
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).build()
        val service: EditProfileContract.Service = retrofit.create(EditProfileContract.Service::class.java)
        call = service.editProfileServicetwo(AuthorizationToken,userId,parent_model_signup)
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
                    Log.e("THIRD_SECTION", response.body()!!.string())

                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true")
                        {
                            val data = object1.optJSONObject("data")
                            val user_location_array = data.optJSONArray("user_Location")
                            if (image != null) {
                                UploadImageTwo()
                                view.saveRegisterDataToPrefs(data)
                            }
                            else {
                                view.saveRegisterDataToPrefs(data)
                            }

                            view.saveRegisterDataToPrefs(data)
                            Log.e("USER_LOCATION_VALUES ", user_location_array.toString())
                        }
                        else
                        {
                            val message = object1.optString("message")
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

    private fun UploadImageTwo()
    {

        val image = view.getImageFile()

        view.showLoader()
        val user_id = RequestBody.create(MediaType.parse("multipart/form-data"),userId)
        var fileReqBody = RequestBody.create(MediaType.parse("image/*"), image);
        var part = MultipartBody.Part.createFormData("profile_pic", image!!.getName(), fileReqBody)

        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
        call = service.Image_upload_User_two(user_id,part)
        Log.e("SECOND_SECTION", call.toString())

        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {
                view.hideLoader()
                if (response.isSuccessful)
                {
                    val res = response.body()!!.string()
                    Log.e("THIRD_SECTION", response.body()!!.string())

                    val object1 = JSONObject(res)
                    val success = object1.optString("success")
                    val message = object1.optString("message")
                    val token = object1.optString("token")
                    if (success == "true")
                    {
                        val data = object1.optJSONObject("data")

                        view.showErrorMessage(message)

                    }
                    else
                    {
                        val message = object1.optString("message")
                        view.showErrorMessage(message)
                    }

                }
                else
                {
                    view.showError("Server Error")
                }
            }
        })


    }

}
