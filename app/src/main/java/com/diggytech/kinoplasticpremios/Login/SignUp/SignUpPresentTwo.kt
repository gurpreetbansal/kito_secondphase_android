//package com.diggytech.kinoplasticpremios.Login.SignUp
//import android.util.Log
//import androidx.fragment.app.FragmentActivity
//import com.diggytech.kinoplasticpremios.Constants
//import com.diggytech.kinoplasticpremios.LocationsModel
//import com.diggytech.kinoplasticpremios.R
//import com.diggytech.kinoplasticpremios.model.LocationArryForUserTwo_Three
//import okhttp3.MediaType
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//import okhttp3.ResponseBody
//import org.json.JSONArray
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.io.File
//import java.util.*
//import kotlin.collections.ArrayList
//
//
//
////import javax.swing.UIManager.put
//
//
//
//
//
//
//
//
//class SignUpPresentTwo(val view: SignUpContract.View) {
//    private var isValueTrue: Boolean? = false
//    private var call: Call<ResponseBody>? = null
//    private var profile_pic: MultipartBody.Part? = null
//    private var myPreferences = "myPrefs"
//    var selection_multiple_locations = ArrayList<LocationArryForUserTwo_Three>();
//    val parts = ArrayList<RequestBody>()
//
//    fun getDetailsOfScreen1(context: FragmentActivity?) {
//        val username = view.getUsername()
//        val cpftwo = view.getCpfTwo()
//        val contact = view.getContact()
//        val image = view.getImageFile()
//        val user_value = view.getUserType()
//
//        if (username.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_username))
//            return
//        }
//        if (cpftwo.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_cpf))
//            return
//        }
//        if (contact.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_contact_number))
//            return
//        }
//
//        if (image == null) {
//            view.movetoscreen2(username, cpftwo, contact, null, user_value)
//        } else {
//            view.movetoscreen2(username, cpftwo, contact, image, user_value)
//        }
//    }
//
//    fun getDetailsOfScreen2(
//        username: String,
//        cpf: String,
//        contact: String,
//        image: File?,
//        context: FragmentActivity?,
//        selectedUserType: Int?
//    ) {
//        val device_type = view.getDeviceType()
//        val device_token = view.getDeviceToken()
//        val location = view.getLocation()
//        val brand = view.getBrand()
//        val city = view.getCity()
//        val state = view.getState()
//        val email = view.getEmail()
//        val password = view.getPassword()
//        val confirm_password = view.getConfirmedPassword()
//        val fcmtoken = view.gefcmtoken()
//
//
//        if (brand.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_brand))
//            return
//        }
//
//        if (location.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_location))
//            return
//        }
//        if (email.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_email))
//            return
//        }
//        if (!Constants.isEmailValid(email)) {
//            view.showError(context!!.getString(R.string.enter_valid_email))
//            return
//        }
//
//        if (password.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_password))
//            return
//        }
//        if (confirm_password.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_confirmed_password))
//            return
//        }
//
//        if (password.trim() != confirm_password.trim()) {
//            view.showError(context!!.getString(R.string.passwords_do_not_match))
//            return
//        }
//        val social_type = ""
//        val social_id = ""
//
//        if (image == null && selectedUserType == 1) {
//            callSignUpService(
//                device_type, device_token, username, cpf, contact, location, brand, email,
//                password, social_type, social_id, null, city, state, fcmtoken
//            )
//        } else /*if (image != null  && selectedUserType==1 )*/ {
//            callSignUpService(
//                device_type, device_token, username, cpf, contact, location, brand,
//                email, password, social_type, social_id, image, city, state, fcmtoken
//            )
//        }
////        /*foruser type 2 and 3*/
////        else if (image == null  && selectedUserType==2 && selectedUserType==3)
////        {
////            callSignUpServiceTwo(device_type, device_token,username,cpf,contact, email,
////                password,social_type,social_id,null,fcmtoken)
////        }
////        else if (image != null  && selectedUserType==2 && selectedUserType==3 )
////        {
////            callSignUpServiceTwo( device_type,device_token, username, cpf, contact,
////                email,password, social_type,social_id,image,fcmtoken
////            )
////        }
//
//
//    }
//
//    private fun callSignUpService(
//        deviceType: String,
//        deviceToken: String,
//        username: String,
//        cpf: String,
//        contact: String,
//        location: String,
//        brand: String,
//        email: String,
//        password: String,
//        socialType: String,
//        socialId: String,
//        image: File?,
//        city: String,
//        state: String,
//        fcmtoken: String
//    ) {
//
//        view.showLoader()
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//
//        if (image != null) {
//            val requestFile = RequestBody.create(MediaType.parse("image*//*"), image)
//            profile_pic =
//                MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestFile)
//        }
//
//
//        val deviceType1 = RequestBody.create(MultipartBody.FORM, deviceType)
//        val deviceToken1 = RequestBody.create(MultipartBody.FORM, deviceToken)
//        val username1 = RequestBody.create(MultipartBody.FORM, username)
//        val cpf1 = RequestBody.create(MultipartBody.FORM, cpf)
//        val contact1 = RequestBody.create(MultipartBody.FORM, contact)
//        val location1 = RequestBody.create(MultipartBody.FORM, location)
//        val brand1 = RequestBody.create(MultipartBody.FORM, brand)
//        val email1 = RequestBody.create(MultipartBody.FORM, email)
//        val password1 = RequestBody.create(MultipartBody.FORM, password)
//        val socialType1 = RequestBody.create(MultipartBody.FORM, socialType)
//        val socialId1 = RequestBody.create(MultipartBody.FORM, socialId)
//        val city1 = RequestBody.create(MultipartBody.FORM, city)
//        val state1 = RequestBody.create(MultipartBody.FORM, state)
//        val fcmtoken = RequestBody.create(MultipartBody.FORM, fcmtoken)
//
//        if (image != null) {
//            call = service.callRegisterService(
//                deviceType1, deviceToken1, username1, cpf1, contact1, location1, brand1,
//                email1, password1, socialType1, socialId1, profile_pic!!, city1, state1, fcmtoken
//            )
//            Log.e("REGISTRATION_TOKEN", call.toString())
//
//        } else {
//            call = service.callRegisterService(
//                deviceType1, deviceToken1, username1, cpf1, contact1, location1, brand1, email1,
//                password1, socialType1, socialId1, null, city1, state1, fcmtoken
//            )
//            Log.e("REGISTRATION_TOKEN", call.toString())
//
//        }
//
//        call!!.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                // view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    Log.e("REGISTRATION_TOKEN", response.body()!!.string())
//
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val token = object1.optString("token")
//                        if (success == "true") {
//                            val data = object1.optJSONObject("data")
//                            view.saveRegisterDataToPrefs(data, token)
//                        } else {
//                            val message = object1.optString("message")
//                            view.showErrorMessage(message)
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//
//
//    }
//
//
//    /*for user type two*/
//    fun getDetailsOfScreen2_UserType(username: String, cpf: String, contact: String, image: File?,
//                                     context: FragmentActivity?, selectedUserType: Int?) {
//
//        val device_type = view.getDeviceType()
//        val device_token = view.getDeviceToken()
//        val email = view.getEmail()
//        val password = view.getPassword()
//        val confirm_password = view.getConfirmedPassword()
//        val fcmtoken = view.gefcmtoken()
////        val getLocationArray = view.getLocationArray(ArrayList<LocationArryForUserTwo_Three>())
////
////        for (i in 0 until getLocationArray.size) {
////            var model = LocationArryForUserTwo_Three()
////            model.brand_value = getLocationArray[i].brand_value
////            model.location_name_value = getLocationArray.get(i).location_name_value
////            model.city_value = getLocationArray.get(i).city_value
////            model.state_value = getLocationArray.get(i).state_value
////            model.id_value = getLocationArray.get(i).id_value
////            selection_multiple_locations.add(model);
////        }
//        var locationModelArrayList: ArrayList<LocationModel_Two> = ArrayList()
//        val list_brand = ArrayList<String>()
//        val list_city = ArrayList<String>()
//        val list_state = ArrayList<String>()
//        val list_location = ArrayList<String>()
//        val list_location_id = ArrayList<String>()
//
//
//        for (test in 0 until selection_multiple_locations.size) {
//            var locationModel = LocationModel_Two()
//            locationModel.locationID = selection_multiple_locations.get(test).getId()
//            locationModel.brand = selection_multiple_locations.get(test).getBrand()
//            locationModel.state = selection_multiple_locations.get(test).getState()
//            locationModel.city = selection_multiple_locations.get(test).getCity()
//            locationModel.locationString = selection_multiple_locations.get(test).getNames()
//            locationModelArrayList!!.add(locationModel)
//
//        }
//
//        for (test in 0 until locationModelArrayList!!.size) {
//            list_brand.add(0,locationModelArrayList.get(test).brand )
//        }
//        for (test in 0 until locationModelArrayList!!.size) {
//            list_state.add(0,locationModelArrayList.get(test).state )
//        }
//        for (test in 0 until locationModelArrayList!!.size) {
//            list_city.add(0,locationModelArrayList.get(test).city )
//        }
//        for (test in 0 until locationModelArrayList!!.size) {
//            list_location.add(0,locationModelArrayList.get(test).locationString)
//        }
//
//        for (test in 0 until locationModelArrayList!!.size) {
//            list_location_id.add(0,locationModelArrayList.get(test).locationID)
//        }
//
//        var map = HashMap<String,ArrayList<String>>()
//        map.put("Brand",list_brand )
//        map.put("State",list_state )
//        map.put("City",list_city )
//        map.put("Location",list_location )
//        map.put("LocationID",list_location_id )
//
//        val mapList = ArrayList<HashMap<String, ArrayList<String>>>()
//        mapList.add(map)
//
//        for (i in 0 until mapList!!.size) {
//            // parts.add( MultipartBody.create("user_Location",""));
//            parts.add(RequestBody.create(MultipartBody.FORM, mapList.get(i).toString()));
//        }
//
//
//
//        if (email.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_email))
//            return
//        }
//        if (!Constants.isEmailValid(email)) {
//            view.showError(context!!.getString(R.string.enter_valid_email))
//            return
//        }
//
//        if (password.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_password))
//            return
//        }
//        if (confirm_password.trim().isEmpty()) {
//            view.showError(context!!.getString(R.string.please_enter_your_confirmed_password))
//            return
//        }
//
//        if (password.trim() != confirm_password.trim()) {
//            view.showError(context!!.getString(R.string.passwords_do_not_match))
//            return
//        }
//
//        if (image == null && selectedUserType == 2 ) {
//
//
//            callSignUpServiceTwo(device_type, device_token, username, cpf, contact, selectedUserType,
//                email, password, null, fcmtoken, parts)
//        }
//        else if (image != null  && selectedUserType==2  )
//        {
//            callSignUpServiceTwo(
//                device_type, device_token, username, cpf, contact, selectedUserType!!,
//                email, password, image, fcmtoken, parts
//            )
//        }
//
//    }
//
//    /*in case of user two and three*/
//    private fun callSignUpServiceTwo(deviceType: String, deviceToken: String, username: String, cpf: String, contact: String,
//                                     selectedUserType: Int, email: String, password: String, image: File?, fcmtoken: String,
//                                     part: ArrayList<RequestBody>
//    ) {
//
//        view.showLoader()
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//
//        if (image != null) {
//            val requestFile = RequestBody.create(MediaType.parse("image*//*"), image)
//            profile_pic =
//                MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestFile)
//        }
//
//
//        val deviceType1 = RequestBody.create(MultipartBody.FORM, deviceType)
//        val deviceToken1 = RequestBody.create(MultipartBody.FORM, deviceToken)
//        val username1 = RequestBody.create(MultipartBody.FORM, username)
//        val cpf1 = RequestBody.create(MultipartBody.FORM, cpf)
//        val contact1 = RequestBody.create(MultipartBody.FORM, contact)
//        val email1 = RequestBody.create(MultipartBody.FORM, email)
//        val password1 = RequestBody.create(MultipartBody.FORM, password)
//        val fcmtoken = RequestBody.create(MultipartBody.FORM, fcmtoken)
//        val selected_user = RequestBody.create(MultipartBody.FORM, selectedUserType.toString())
//
//        if (image != null) {
//            //Log.e("REGISTRATION_TOKEN1", call.toString())
////            call = service.callRegisterServiceUserTypeTwo(
////                deviceType1, deviceToken1, username1, cpf1, contact1,
////                selected_user, part,
////                email1, password1, profile_pic, fcmtoken
////            )
//            Log.e("FIRST_SECTION", call.toString())
//
//        } else {
////            call = service.callRegisterServiceUserTypeTwo(
////                deviceType1, deviceToken1, username1, cpf1, contact1,
////                selected_user, part,
////                email1, password1, null, fcmtoken
////            )
//            Log.e("SECOND_SECTION", call.toString())
//
//        }
//
//        call!!.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                // view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    Log.e("THIRD_SECTION", response.body()!!.string())
//
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val token = object1.optString("token")
//                        if (success == "true") {
//                            val data = object1.optJSONObject("data")
//                            view.saveRegisterDataToPrefs(data, token)
//                        } else {
//                            val message = object1.optString("message")
//                            view.showErrorMessage(message)
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Rajeshwasri yadav")
//                }
//            }
//        })
//
//
//    }
//
//
//    /*get brand*/
//    fun getAllBrands() {
//        view.showLoader()
//
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//        val call: Call<ResponseBody> = service.getBrands()
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                //  view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val message = object1.optString("message")
//                        if (success == "true") {
//                            val data = object1.optJSONArray("data")
//                            // data.put(0,"select any Value")
//                            view.saveLocations(parseLocations(data))
//                        } else {
//                            view.showErrorMessage(message)
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//    }
//
//    fun getAllLocations(brand: String) {
//        view.showLoader()
//
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//        val call: Call<ResponseBody> = service.getLocations(brand)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                //view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val message = object1.optString("message")
//                        if (success == "true") {
//
//                            val data = object1.optJSONArray("data")
//                            view.saveBrandLocations(parseLocations(data))
//                        } else {
//                            view.showErrorMessage(message)
//                        }
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//    }
//
//    //getAllCityLocations
//    fun getAllCityLocations(state: String, brand: String) {
//        view.showLoader()
//
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//        val call: Call<ResponseBody> = service.getCityLocations(state, brand)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                //view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val message = object1.optString("message")
//                        if (success == "true") {
//
//                            val data = object1.optJSONArray("data")
//                            view.saveBrandCityLocations(parseLocations(data))
//                        } else {
//                            view.showErrorMessage(message)
//                        }
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//    }
//
//    /*get valid cpf*/
//    fun getcpfvalid(cpf: String, isTrue: Boolean) {
//        isValueTrue = isTrue
//        view.showLoader()
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//        val call: Call<ResponseBody> = service.getCpfValid(cpf)
//        Log.e("CPF_VALUES", cpf)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    try {
//                        val object1 = JSONObject(res)
//                        val code = object1.optInt("code")
//                        val success = object1.optString("success")
//                        val message = object1.optString("message")
//
//                        if (success.equals("true")) {
//                            Log.e("SIGNUP_PRESENTER", success)
//                            view.showImage()
//
//                        } else {
//                            if (!isValueTrue!!) {
//                                isValueTrue = true
//                                view.showErrorMessageTwo(message)
//                            }
//                        }
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//    }
//
//
//    //getAllLocation
//    fun getLocations(city: String, brand: String, user_type_value: Int) {
//        view.showLoader()
//        var user_value = user_type_value
//        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
//        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
//        val call: Call<ResponseBody> = service.getAll_Locations(city, brand, user_type_value)
//        call.enqueue(object : Callback<ResponseBody> {
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                view.hideLoader()
//                //  view.showError("No internet connection")
//            }
//
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                view.hideLoader()
//                if (response.isSuccessful) {
//                    val res = response.body()!!.string()
//                    try {
//                        val object1 = JSONObject(res)
//                        val success = object1.optString("success")
//                        val message = object1.optString("message")
//                        if (success == "true") {
//                            var data = object1.optJSONArray("data")
//                            view.saveAllLocations(parseLocations(data))
//
//                            for (i in 0 until data.length())
//                            {
//                                var obj = data.getJSONObject(i)
//                                if (user_value != 1) {
//                                    if (obj.getString("status").equals("1")) {
//                                        view.showErrorMessage(message)
//                                        view.showRecyclerViewgone()
//                                    } else {
//
//                                        view.showRecyclerView()
//
//                                    }
//                                } else {
//                                    view.showRecyclerViewgone()
//                                }
//                            }
//
//                        } else {
//                            view.showErrorMessage(message)
//                        }
//
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    view.showError("Server Error")
//                }
//            }
//        })
//    }
//
//
//    private fun parseLocations(data: JSONArray): MutableList<LocationsModel> {
//        val list = mutableListOf<LocationsModel>()
//        var isTrue: Boolean? = true
//        for (i in 0 until data.length()) {
//            val model = LocationsModel()
//            val object1 = data.optJSONObject(i)
//            model.id = object1.optString("id")
//            model.name = object1.optString("name")
//            model.state = object1.optString("state")
//            model.city = object1.optString("city")
//            model.brand = object1.optString("brand")
//            model.status = object1.optString("status")
//
//
//
//            list.add(model)
//
//
//            //if(sta)
//        }
//        return list
//
//    }
//
//
//}
//
//
