package com.diggytech.kinoplasticpremios.Login.SignUp

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.R
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
import kotlin.collections.ArrayList
import android.content.SharedPreferences
import com.app.soyadeti.Interface.APIService


class SignUpPresenter(val view: SignUpContract.View) {
    private var isValueTrue: Boolean? = false
    private var call: Call<ResponseBody>? = null
    private var profile_pic: MultipartBody.Part? = null
    private var myPreferences = "myPrefs"
    var selection_multiple_locations = ArrayList<SignUpRequest_ChlidDataModel>();
    val parts = ArrayList<RequestBody>()
    var parent_model_signup =SignUpRequest_ParentDataModel()


    /*for second user shared preference */
    var shared: SharedPreferences? = null
    var arrPackage: ArrayList<String>? = null
    val list_child = mutableListOf<SignUpResponse_ChildeDataModel>()
    var context: FragmentActivity? = null

     var user_id_multiple:String=""
    var mAPIService: APIService? = null

    fun getDetailsOfScreen1(context: FragmentActivity?) {
        val username = view.getUsername()
        val cpftwo = view.getCpfTwo()
        val contact = view.getContact()
        val image = view.getImageFile()
        val user_value = view.getUserType()
        val gender = view.getGender()
        val dob =view.getDOB()



        if (username.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_username))
            return
        }
        if (cpftwo.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_cpf))
            return
        }
        if (contact.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_contact_number))
            return
        }

        if (image == null) {
            view.movetoscreen2(username, cpftwo, contact, null, user_value,gender,dob)
        } else {
            view.movetoscreen2(username, cpftwo, contact, image, user_value,gender,dob)
        }
    }

    fun getDetailsOfScreen2(
        username: String,
        cpf: String,
        contact: String,
        image: File?,
        context: FragmentActivity?,
        selectedUserType: String?,
        selectedGenderType: String?,
        dob: String
    ) {
        val device_type = view.getDeviceType()
        val device_token = view.getDeviceToken()
        val location = view.getLocation()
        val brand = view.getBrand()
        val city = view.getCity()
        val state = view.getState()
        val email = view.getEmail()
        val password = view.getPassword()
        val confirm_password = view.getConfirmedPassword()
        val fcmtoken = view.gefcmtoken()


        if (brand.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_brand))
            return
        }

        if (location.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_location))
            return
        }
        if (email.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_email))
            return
        }
        if (!Constants.isEmailValid(email)) {
            view.showError(context!!.getString(R.string.enter_valid_email))
            return
        }
        if (dob.trim().isEmpty()){
            view.showError(context!!.getString(R.string.please_enter_dob))
            return
        }

        if (password.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_password))
            return
        }
        if (confirm_password.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_confirmed_password))
            return
        }

        if (password.trim() != confirm_password.trim()) {
            view.showError(context!!.getString(R.string.passwords_do_not_match))
            return
        }
        val social_type = ""
        val social_id = ""

        if (image == null) {
            callSignUpService(
                device_type, device_token, username, cpf, contact, location, brand, email,
                password, social_type, social_id, null, city, state, fcmtoken, selectedUserType.toString(),
                selectedGenderType.toString(),dob
            )
        } else /*if (image != null  && selectedUserType==1 )*/ {
            callSignUpService(
                device_type,
                device_token,
                username,
                cpf,
                contact,
                location,
                brand,
                email,
                password,
                social_type,
                social_id,
                image,
                city,
                state,
                fcmtoken,
                selectedUserType.toString(),
                selectedGenderType.toString(),
                dob
            )
        }
//        /*foruser type 2 and 3*/
//        else if (image == null  && selectedUserType==2 && selectedUserType==3)
//        {
//            callSignUpServiceTwo(device_type, device_token,username,cpf,contact, email,
//                password,social_type,social_id,null,fcmtoken)
//        }
//        else if (image != null  && selectedUserType==2 && selectedUserType==3 )
//        {
//            callSignUpServiceTwo( device_type,device_token, username, cpf, contact,
//                email,password, social_type,social_id,image,fcmtoken
//            )
//        }


    }

    private fun callSignUpService(
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
        socialId: String,
        image: File?,
        city: String,
        state: String,
        fcmtoken: String,
        selectedUserType: String,
        selectedGenderType: String?,
        dob: String
    ) {

        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)

        if (image != null) {
            val requestFile = RequestBody.create(MediaType.parse("image*//*"), image)
            profile_pic =MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestFile)
        }


        val deviceType1 = RequestBody.create(MultipartBody.FORM, deviceType)
        val deviceToken1 = RequestBody.create(MultipartBody.FORM, deviceToken)
        val username1 = RequestBody.create(MultipartBody.FORM, username)
        val cpf1 = RequestBody.create(MultipartBody.FORM, cpf)
        val contact1 = RequestBody.create(MultipartBody.FORM, contact)
        val location1 = RequestBody.create(MultipartBody.FORM, location)
        val brand1 = RequestBody.create(MultipartBody.FORM, brand)
        val email1 = RequestBody.create(MultipartBody.FORM, email)
        val password1 = RequestBody.create(MultipartBody.FORM, password)
        val socialType1 = RequestBody.create(MultipartBody.FORM, socialType)
        val socialId1 = RequestBody.create(MultipartBody.FORM, socialId)
        val city1 = RequestBody.create(MultipartBody.FORM, city)
        val state1 = RequestBody.create(MultipartBody.FORM, state)
        val fcmtoken = RequestBody.create(MultipartBody.FORM, fcmtoken)
        val user_type = RequestBody.create(MultipartBody.FORM, selectedUserType)
        val gender = RequestBody.create(MultipartBody.FORM,selectedGenderType)
        val dob = RequestBody.create(MultipartBody.FORM,dob)

        if (image != null) {
            call = service.callRegisterService(
                deviceType1, deviceToken1, username1, cpf1, contact1, location1, brand1,
                email1, password1, socialType1, socialId1, profile_pic, city1, state1, fcmtoken,user_type,
                gender,dob
            )
            Log.e("REGISTRATION_TOKEN", call.toString())

        } else {
            call = service.callRegisterService(
                deviceType1, deviceToken1, username1, cpf1, contact1, location1, brand1, email1,
                password1, socialType1, socialId1, null, city1, state1, fcmtoken
            ,user_type,gender,dob)
            Log.e("REGISTRATION_TOKEN", call.toString())

        }

        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    Log.e("REGISTRATION_TOKEN", response.body()!!.string())

                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true") {
                            val data = object1.optJSONObject("data")
                            view.saveRegisterDataToPrefs(data, token)
                        } else {
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


    /*for user type two*/
    fun getDetailsOfScreen2_UserType(
        username: String,
        cpf: String,
        contact: String,
        image: File?,
        context: FragmentActivity?,
        selectedUserType: String,
        selectedGenderType: String,
        dob: String
    ) {

        val device_type = view.getDeviceType()
        val device_token = view.getDeviceToken()
        val email = view.getEmail()
        val password = view.getPassword()
        val confirm_password = view.getConfirmedPassword()
        val fcmtoken = view.gefcmtoken()

        val getLocationArray = view.getLocationArray(ArrayList<SignUpRequest_ChlidDataModel>())

        for (i in 0 until getLocationArray.size) {
            var model = SignUpRequest_ChlidDataModel()
            model.brand = getLocationArray[i].brand
            model.location = getLocationArray.get(i).location
            model.city = getLocationArray.get(i).city
            model.state = getLocationArray.get(i).state
            model.locationID = getLocationArray.get(i).locationID
            selection_multiple_locations.add(model);
        }

        parent_model_signup.email=email
        parent_model_signup.cpf_number=cpf
        parent_model_signup.device_token=device_token
        parent_model_signup.device_type=device_type
        parent_model_signup.fcmtoken=fcmtoken
        parent_model_signup.password=password
        parent_model_signup.phone_number=contact
        parent_model_signup.profile_pic= image
        parent_model_signup.user_type= selectedUserType.toString()
        parent_model_signup.username=username
        parent_model_signup.user_Location=selection_multiple_locations



        if (email.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_email))
            return
        }
        if (!Constants.isEmailValid(email)) {
            view.showError(context!!.getString(R.string.enter_valid_email))
            return
        }

        if (password.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_password))
            return
        }
        if (confirm_password.trim().isEmpty()) {
            view.showError(context!!.getString(R.string.please_enter_your_confirmed_password))
            return
        }

        if (password.trim() != confirm_password.trim()) {
            view.showError(context!!.getString(R.string.passwords_do_not_match))
            return
        }

        if ( selectedUserType.equals("2")||selectedUserType.equals("3"))
        {
            Log.e("SENDING_DATA","SENDING_DATA"+parent_model_signup)
            //callSignUpServiceTwo()
//            if (image == null) {
//                callSignUpServiceTwo()
//            }
//            else {

                callSignUpServiceTwo()

         //   }


        }
    }

    /*in case of user two and three*/
    private fun callSignUpServiceTwo()
    {
        //val image = view.getImageFile()
        view.showLoader()
       // val requestFile = RequestBody.create(MediaType.parse("image*//*"), image)
       // profile_pic =MultipartBody.Part.createFormData("profile_pic", "profile_pic.png", requestFile)
        val image = view.getImageFile()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
            call = service.callRegisterServiceUserTypeTwo(parent_model_signup )
            Log.e("SECOND_SECTION", call.toString())

        call!!.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                view.hideLoader()
                // view.showError("No internet connection")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>)
            {
                view.hideLoader()
                if (response.isSuccessful) {
                    val res = response.body()!!.string()
                    Log.e("THIRD_SECTION", response.body()!!.string())

//                    try {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val token = object1.optString("token")
                        if (success == "true")
                        {
                            val data = object1.optJSONObject("data")

                            user_id_multiple= data.optInt("id").toString()
                           // val preferences = PreferenceManager.getDefaultSharedPreferences(context!!)
                            //preferences.edit().putString("USER_ID_VALUE", user_id_multiple).apply()
                            view.saveUerIdToPrefs(data,user_id_multiple)

                                        if (image != null) {
                                            UploadImageTwo()
                                            view.saveRegisterDataToPrefs_UserTypeTwo(data, token)
            }
else {
                                            view.saveRegisterDataToPrefs_UserTypeTwo(data, token)
                                        }
                           // var user_type=data.getString("user_type")

                           // val user_location_array = data.optJSONArray("user_Location")
                           // view.saveRegisterDataToPrefs_UserTypeTwo(data, token)


                           //var user_type = data.getJSONObject("user_type")
                          // var user_id_multiple = data.getInt("id")

                           // view.save_userpe(user_type)
                           // Log.e("USER_LOCATION_VALUES ", user_location_array.toString())
                           // Log.e("USER_ID_VALUE ", user_id_multiple.toString())

//                            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
//                            preferences.edit().putString("USER_TYPESS", user_type.toString()).apply()
//



                        }
                        else
                        {
                            val message = object1.optString("message")
                            view.showErrorMessage(message)
                        }
                  //  }
//                    catch (e: Exception)
//                    {
//                        e.printStackTrace()
//                    }
                } else {
                    view.showError("Server Error")
                }
            }
        })


    }

    private fun UploadImageTwo()
    {
//         var from_where_come = PreferenceManager.getDefaultSharedPreferences(context).getString("USER_ID_VALUE","")
//        Log.e("USER_TYPE_IS", from_where_come.toString())
       // var user_type = Constants.getPrefs(context!!)!!.getString(Constants.USER_ID, "")
        val image = view.getImageFile()
        val user_id_value = view.getUserIdD()

        view.showLoader()
        val user_id = RequestBody.create(MediaType.parse("multipart/form-data"),user_id_value)
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

//                    try
//                    {
                        val object1 = JSONObject(res)
                        val success = object1.optString("success")
                        val message = object1.optString("message")
                         val token = object1.optString("token")
                    if (success == "true")
                    {
                        val data = object1.optJSONObject("data")


                            //val data = object1.optJSONArray("data")
                            view.showErrorMessage(message)
                       // val intent = Intent(context, DashBoardActivity::class.java)
                       // startActivity(intent)
                            //view.saveRegisterDataToPrefs_UserTypeTwo(data,token)
                        }
                        else
                        {
                            val message = object1.optString("message")
                            view.showErrorMessage(message)
                        }
//                    }
//                    catch (e: Exception)
//                    {
//                        e.printStackTrace()
//                    }
                }
                else
                {
                    view.showError("Server Error")
                }
            }
        })


    }



//    fun UploadImageTwo() {
//        mAPIService = APIService.ApiUtils.apiService
//       view.showLoader()
//        val image = view.getImageFile()
//        var fileReqBody = RequestBody.create(MediaType.parse("image/*"), image);
//        // Create MultipartBody.Part using file request-body,file name and part name
//        var part = MultipartBody.Part.createFormData("profile_pic", image?.getName(), fileReqBody)
//
//
//        var description2 = RequestBody.create(MediaType.parse("multipart/form-data"),user_id_multiple)
//        Log.e("UPDATE_DATA","" + " " + part + description2 )
//
//        var map = HashMap<String, RequestBody>()
//        map.put("userid", description2)
//
//
//
//        mAPIService!!.Update_Image(part, map).enqueue(object : Callback<UploadImageResponse> {
//
//            @SuppressLint("NewApi")
//            override fun onResponse(
//                call: Call<UploadImageResponse>,
//                response: Response<UploadImageResponse>
//            ) {
//               view.hideLoader()
//                if (response.isSuccessful()) {
//
//                    if (response.body()!!.code == 200)
//                    {
//                        Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
//                    }
//                    else {Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
//                    }
//                   // Toast.makeText(context,response.body()!!.message,Toast.LENGTH_SHORT).show()
//
//                }
//               // Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
//            }
//
//
//            override fun onFailure(call: Call<UploadImageResponse>, t: Throwable) {
//               view.hideLoader()
//                t.printStackTrace()
//
//            }
//        })
//
//
//    }



    /*get valid cpf*/
    fun getcpfvalid(cpf: String, isTrue: Boolean) {
        isValueTrue = isTrue
        view.showLoader()
        val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL).build()
        val service: SignUpContract.Service = retrofit.create(SignUpContract.Service::class.java)
        val call: Call<ResponseBody> = service.getCpfValid(cpf)
        Log.e("CPF_VALUES", cpf)
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
                        val code = object1.optInt("code")
                        val success = object1.optString("success")
                        val message = object1.optString("message")

                        if (success.equals("true")) {
                            Log.e("SIGNUP_PRESENTER", success)
                            view.showImage()

                        } else {
                            if (!isValueTrue!!) {
                                isValueTrue = true
                                view.showErrorMessageTwo(message)
                            }
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
        var isTrue: Boolean? = true
        for (i in 0 until data.length()) {
            val model = LocationsModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optString("id")
            model.name = object1.optString("name")
            model.state = object1.optString("state")
            model.city = object1.optString("city")
            model.brand = object1.optString("brand")
            model.status = object1.optString("status")
            list.add(model)
            //if(sta)
        }
        return list

    }

    private fun parseUserType_Two_ChildData(user_Location: JSONArray): MutableList<SignUpResponse_ChildeDataModel> {

        var isTrue: Boolean? = true
        for (i in 0 until user_Location.length())
        {
            val model = SignUpResponse_ChildeDataModel()
            val object1 = user_Location.optJSONObject(i)
            model.city = object1.optString("City")
            model.locationID = object1.optString("LocationID")
            model.location = object1.optString("Location")
            model.brand = object1.optString("Brand")
            model.state = object1.optString("State")
            list_child.add(model)
        }
        return list_child

    }


    private fun parseUserType_Two_Data(data: JSONArray): MutableList<SignUpResponse_ParentDataModel> {
        val list = mutableListOf<SignUpResponse_ParentDataModel>()
        var isTrue: Boolean? = true
        for (i in 0 until data.length())
        {
            val model = SignUpResponse_ParentDataModel()
            val object1 = data.optJSONObject(i)
            model.id = object1.optInt("id")
            model.email = object1.optString("email")
            model.address = object1.optString("address")
            model.cpf_number = object1.optString("cpf_number")
            model.phone_number = object1.optString("phone_number")
            model.username = object1.optString("username")
            model.device_token = object1.optString("device_token")
            model.user_Location.addAll(list_child)
            list.add(model)


        }
        return list

    }
}


