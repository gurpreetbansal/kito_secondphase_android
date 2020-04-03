package com.diggytech.kinoplasticpremios.DashBoard

import com.diggytech.kinoplasticpremios.LocationsModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST

class DashBoardContract {
    interface View {
        fun setUpBottomNavigation()
        fun showLoader()
        fun hideLoader()
        fun showError(error: String)
        fun showErrorMessage(message: String?)
        fun saveLocations(data: MutableList<LocationsModel>)

    }

    interface Service {

        @POST("auth/getAllLocations")
        fun getLocations(): Call<ResponseBody>

    }

}