package com.diggytech.kinoplasticpremios.Profile.EditProfile

import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.Profile.ViewProfile.ModelProfile
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import java.io.File

class EditProfileContract {
    interface View {
        fun getUserId(): String
        fun getAuthToken(): String
        fun showLoader()
        fun hideLoader()
        fun showError(message: String)
        fun showErrorMessage(message: String?)
        fun setProfileData(model: ModelProfile)
        fun getUsername(): String
        fun getCpf(): String
        fun getContact(): String
        fun getImageFile(): File?
        fun getLocation(): String
      fun getBrand(): String
        fun saveRegisterDataToPrefs(data: JSONObject?)
        fun getCity(): String
        fun getState(): String

        fun getUserType(): String

//        fun saveLocations(locations: MutableList<LocationsModel>)
//        fun saveStateData(locations: MutableList<LocationsModel>)
//        fun saveCityData(locations: MutableList<LocationsModel>)
//        fun saveLocationsData(locations: MutableList<LocationsModel>)


        fun getLocationArray(locationsArray: ArrayList<EditProfileRequest_ChildDataModel>): ArrayList<EditProfileRequest_ChildDataModel>
    }

    interface Service {
        @POST("auth/getProfile")
        fun getProfileService(): Call<ResponseBody>


        @Multipart
        @POST("auth/editProfile")
        fun editProfileService(
            @Part profilePic: MultipartBody.Part?,
            @Part("username") username1: RequestBody,
            @Part("cpf_number") cpf1: RequestBody,
            @Part("phone_number") contact1: RequestBody,
            @Part("address") location1: RequestBody,
            @Part("brand") brand1: RequestBody,
            @Part("city") city1: RequestBody,
            @Part("state") state1: RequestBody,
            @Part("user_type") user_type: RequestBody
        ): Call<ResponseBody>

        @POST("auth/editProfile")
        fun editProfileServicetwo(
            @Header("AuthorizationToken") authorization_token: String, @Header("userId") user_id: String,
            @Body sendFamilyData: EditProfileRequest_ParentDataModel
        ): Call<ResponseBody>

    }

}
