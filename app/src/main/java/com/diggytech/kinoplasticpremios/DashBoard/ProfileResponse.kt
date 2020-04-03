package com.diggytech.kinoplasticpremios.DashBoard

data class ProfileResponse(
    val code: Int,
    val `data`: ProfileResponseData,
    val message: String,
    val success: Boolean
)