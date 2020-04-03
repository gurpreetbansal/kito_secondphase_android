package com.diggytech.kinoplasticpremios.Login.SignUp

data class CityListResponse(
    val code: Int,
    val `data`: List<CityListData>,
    val message: String,
    val success: Boolean
)