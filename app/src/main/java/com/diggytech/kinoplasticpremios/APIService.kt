package com.app.soyadeti.Interface


import com.app.soyadeti.utilityClasses.RetrofitClient
import com.diggytech.kinoplasticpremios.Config
import com.diggytech.kinoplasticpremios.DashBoard.ProfileResponse
import com.diggytech.kinoplasticpremios.Login.SignUp.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart


interface APIService {

//PROFILE
    //@Multipart
    @POST("auth/getProfile")
    fun Profile(@Header("AuthorizationToken") authorization_token: String,@Header("userId") user_id: String): Call<ProfileResponse>

    @POST("auth/getAllLocations")
    fun Location_List( @Body data: LocationListRequest): Call<LocationListResponse>

    @POST("auth/getAllBrands")
    fun Brand_List():Call<BrandListResponse>

    @POST("auth/getAllState")
    fun State_List( @Body data: StateListRequest): Call<StateListResponse>

    @POST("auth/getAllCity")
    fun City_List( @Body data: CityListRequest): Call<CityListResponse>


//    @Multipart
//    @POST("uploadimage")
//    fun Update_Image(
//        @Part file: MultipartBody.Part,
//        @PartMap map: HashMap<String, RequestBody>): Call<UploadImageResponse>
  @Multipart
    @POST("uploadimage")
    fun Update_Image(
        @Part file: MultipartBody.Part,
        @Part map: HashMap<String, RequestBody>): Call<UploadImageResponse>

    object ApiUtils {
        val apiService: APIService
            get() = RetrofitClient.getClient(Config.BASE_URL)!!.create(APIService::class.java)

    }
}

