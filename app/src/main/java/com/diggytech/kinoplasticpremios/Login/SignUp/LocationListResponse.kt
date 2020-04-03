package com.diggytech.kinoplasticpremios.Login.SignUp

data class LocationListResponse(
    val code: Int,
    val `data`: List<LocationListData>,
    val message: String,
    val success: Boolean
)