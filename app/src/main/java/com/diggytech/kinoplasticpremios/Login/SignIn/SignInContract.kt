package com.diggytech.kinoplasticpremios.Login.SignIn

import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SignInContract {
    interface View {
        fun getEmail(): String
        fun getPassword(): String
        fun getDeviceType(): String
        fun getDeviceToken(): String
        fun showError(message: String)
        fun showLoader()
        fun hideLoader()
        fun saveLoginDataToPrefs(data: JSONObject?, token: String?)
        fun showErrorMessage(message: String?)
        fun setFacebookSignUp()
        fun setGoogleSignUp()
        fun gefcmtoken(): String
      //  fun save_userpe(): String

        fun saveLocations(locations: MutableList<LocationsModel>)

    }

    interface Service {

        @FormUrlEncoded
        @POST("auth/login")
        fun callLoginService(
            @Field("email") email: String,
            @Field("password") password: String,
            @Field("device_type") device_type: String,
            @Field("device_token") device_token: String,
            @Field("fcmtoken") fcm_token: String
        ): Call<ResponseBody>

/*{'email': 'testuser@gmail.com','social_type' : 'facebook','social_id':'eqrwrere','device_type':'android','device_token':'243242df43234' }*/

        /*https://platinumitmedia.com/kinoplastic_server/api/auth/socialLogin*/
        @FormUrlEncoded
        @POST("auth/socialLogin")
        fun callfacebookLoginService(
            @Field("email") email: String,
            @Field("social_type") social_type: String,
            @Field("social_id") social_id: String,
            @Field("device_type") device_type: String,
            @Field("device_token") device_token: String,
            @Field("state") state: String,
            @Field("city") city: String
        ): Call<ResponseBody>



        @FormUrlEncoded
        @POST("auth/register")
        fun callRegisterService(
            @Field("device_type") deviceType1: String,
            @Field("device_token") deviceToken1: String,
            @Field("username") username1: String,
            @Field("cpf_number") cpf1: String,
            @Field("phone_number") contact1: String,
            @Field("address") location1: String,
            @Field("brand") brand1: String,
            @Field("email") email1: String,
            @Field("password") password1: String,
            @Field("social_type") socialType1: String,
            @Field("social_id") socialId1: String
        ): Call<ResponseBody>


        @POST("auth/getAllLocations")
        fun getLocations(): Call<ResponseBody>
    }

}
