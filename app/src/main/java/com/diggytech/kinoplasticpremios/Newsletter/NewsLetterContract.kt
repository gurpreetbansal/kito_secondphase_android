package com.diggytech.kinoplasticpremios.Newsletter

import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ChlidDataModel
import com.diggytech.kinoplasticpremios.Login.SignUp.SignUpRequest_ParentDataModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

class NewsLetterContract {
    interface View {
        fun setAdapter(list: MutableList<ModelNewsLetter>)
        fun getLocationId(): String

        fun getLocationId_Two(): String
//        fun getBrand_Two(): String
        fun getUserType(): String

        fun getBrandName(): String
        fun getBrandName_All(): String

        fun getUserId(): String
        fun getAuthToken(): String

        fun showLoader()
        fun hideLoader()
        fun showEmptyListText()
        fun showError(message: String)
        fun showErrorMessage(message: String?)
        fun showSuccessMessage(message: String?)

        fun getLocationArray(locationsArray: ArrayList<ChildNewsDataModel>): ArrayList<ChildNewsDataModel>


    }

    interface Service {
      // @FormUrlEncoded
//        @GET("notificationlist")
//        fun callGetNotificationList(): Call<ResponseBody>

        @FormUrlEncoded
        @POST("notificationlist")
        fun callGetNotificationList(
            @Field("location") location_id: String,
            @Field("brand") brand: String

        ): Call<ResponseBody>


        @POST("notificationlist")
        fun newsTwo(
            @Body sendParent_Data: ParentNewsdataModel
        ): Call<ResponseBody>




    }

}
