package com.android.skephome.OwnerData.ChangePassword

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class ChangePasswordContract {
    interface View {
        fun getOldPass(): String
        fun getNewPass(): String
        fun getConfirmPass(): String
        fun getUserId(): String
        fun getAuthToken(): String
        fun getUserType(): String
        fun showError(error: String)
        fun showLoader()
        fun hideLoader()
        fun showSuccessMessage(message: String)
        fun showErrorMessage(message: String)
    }

    interface Service {
        @FormUrlEncoded
        @POST("auth/changePassword")
        fun callChangePasswordService(
            @Field("old_password") old_password: String,
            @Field("new_password") new_password: String,
            @Field("confirm_password") confirm_password: String
        ): Call<ResponseBody>

    }

}
