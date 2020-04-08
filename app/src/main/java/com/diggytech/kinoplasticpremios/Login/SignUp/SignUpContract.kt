package com.diggytech.kinoplasticpremios.Login.SignUp

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import java.io.File
import retrofit2.http.POST



class SignUpContract {
    interface View {
        fun getUsername(): String
        //fun getCpf(): String
        fun getCpfTwo(): String
        fun getContact(): String
        fun showError(message: String)
       // fun activity_value(message: String)
        fun movetoscreen2(username: String, cpf: String, contact: String, image: File?,user_type_value: String)
        fun getDeviceType(): String
        fun getDeviceToken(): String

        fun getGender(): String
        fun getDOB(): String

        fun getLocation(): String
        fun getLocationArray(locationsArray: ArrayList<SignUpRequest_ChlidDataModel>): ArrayList<SignUpRequest_ChlidDataModel>


        fun getBrand(): String
        fun getEmail(): String
        fun getPassword(): String
        fun getConfirmedPassword(): String

        fun gefcmtoken(): String
        fun  getUserIdD(): String


        fun showLoader()
        fun showImage()

        fun hideLoader()

        fun saveRegisterDataToPrefs(data: JSONObject?, token: String?)
        /*for user type*/
        fun saveRegisterDataToPrefs_UserTypeTwo(data: JSONObject?, token: String?)
        fun saveUerIdToPrefs(data: JSONObject?, token: String?)

        fun showErrorMessage(message: String)

        fun showRecyclerView()
        fun showRecyclerViewgone()

        fun showErrorMessageTwo(message: String)
        fun getImageFile(): File?

        fun getCity(): String
        fun getState(): String
        fun getUserType(): String
        fun validcpf()


        /*for user one*/
//        fun saveLocations(locations: MutableList<LocationsModel>)
//        fun saveBrandLocations(locations: MutableList<LocationsModel>)
//        fun saveBrandCityLocations(locations: MutableList<LocationsModel>)
//        fun saveAllLocations(locations: MutableList<LocationsModel>)
    fun movetoscreen2(
            username: String,
            cpf: String,
            contact: String,
            image: File?,
            user_type: String,
            gender: String,
            dob: String
        )
    }

    interface Service {

        @Multipart
        @POST("auth/register")
        fun callRegisterService(
            @Part("device_type") deviceType1: RequestBody,
            @Part("device_token") deviceToken1: RequestBody,
            @Part("username") username1: RequestBody,
            @Part("cpf_number") cpf1: RequestBody,
            @Part("phone_number") contact1: RequestBody,
            @Part("address") location1: RequestBody,
            @Part("brand") brand1: RequestBody,
            @Part("email") email1: RequestBody,
            @Part("password") password1: RequestBody,
            @Part("social_type") socialType1: RequestBody,
            @Part("social_id") socialId1: RequestBody,
            @Part profilePic: MultipartBody.Part?,
            @Part("city") city1: RequestBody,
            @Part("state") state1: RequestBody,
            @Part("fcmtoken") fcmtoken: RequestBody,
            @Part("user_type") user_type: RequestBody,
            @Part("dob") dob: RequestBody,
            @Part("gender") gender: RequestBody

        ): Call<ResponseBody>
/* @Part("user_Location[]") map: HashMap<String, RequestBody>,*/
        /*"email":"manojtesting1001@gmail.com",
	"phone_number":"+55123-45678-4548",
	"device_type":"ios",
	"device_token":"f2e03f6e2a81c4974c883bb4fbd896a83d388da21158047310de94c849072d10",
	"user_Location":[{"Brand":"CITROEN","Location":"CANNES","City":"MACA\u00c9","State":"RJ","LocationID":"99"}],
	"password":"123456789",
	"username":"manoj",
	"user_type":"2",
	"cpf_number":"5432167890",
	"profile_pic":null,
	"fcmtoken":"fVBnwZK6R9ktwD_Cb"*/

        /*ealier 11 dec*/
        /* @Multipart
        @POST("auth/register")
        fun callRegisterServiceUserTypeTwo(
            @Part("device_type") deviceType1: RequestBody,//
            @Part("device_token") deviceToken1: RequestBody,//
            @Part("username") username1: RequestBody,//
            @Part("cpf_number") cpf1: RequestBody,//
            @Part("phone_number") contact1: RequestBody,  //
            @Part("user_type") user_type_value: RequestBody,//
            @Part("user_Location[]")title: ArrayList<RequestBody> ,
            @Part("email") email1: RequestBody,//
            @Part("password") password1: RequestBody,//
            @Part profilePic: MultipartBody.Part?,//
            @Part("fcmtoken") fcmtoken: RequestBody//
        ): Call<ResponseBody>*/



      //  @Multipart
      //  @POST("auth/register")
       // fun callRegisterServiceUserTypeTwo(@Body  example:SignUpRequest_ParentDataModel): Call<ResponseBody>



        @POST("auth/register")
        fun callRegisterServiceUserTypeTwo(
            @Body sendParent_Data: SignUpRequest_ParentDataModel
        ): Call<ResponseBody>


        @Multipart
        @POST("uploadimage")
        fun Image_upload_User_two(
            @Part("userid") user_id: RequestBody,
            @Part postImage: MultipartBody.Part
        ): Call<ResponseBody>





        /*  @Field("user_Location")lostProjectReasons:ArrayList<LocationArryForUserTwo_Three> ,*/

/*   @Part("social_type") socialType1: RequestBody,
            @Part("social_id") socialId1: RequestBody,*/
//        @PartMap  map: HashMap<String, RequestBody>,

        /*for brand*/
        @POST("auth/getAllBrands")
        fun getBrands(): Call<ResponseBody>
     /*for cpf*/
     @FormUrlEncoded
     @POST("auth/validatecpf")
     fun getCpfValid(@Field("cpf") cpf: String): Call<ResponseBody>

/*for state*/

        @FormUrlEncoded
       // @POST("auth/getAllLocations")
        @POST("auth/getAllState")
        fun getLocations(
            @Field("brand") brand: String
        ): Call<ResponseBody>

        /*for city*/

        @FormUrlEncoded
        // @POST("auth/getAllLocations")
        @POST("auth/getAllCity")
        fun getCityLocations(
            @Field("state") state: String,
            @Field("brand") brand: String
        ): Call<ResponseBody>
/*get location*/
        @FormUrlEncoded
        @POST("auth/getAllLocations")
        fun getAll_Locations(
            @Field("city") city: String,
            @Field("brand") brand: String,
            @Field("user_type") user_type_value: String
        ): Call<ResponseBody>





    }

}
