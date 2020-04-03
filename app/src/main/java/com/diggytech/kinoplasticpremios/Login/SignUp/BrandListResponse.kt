package com.diggytech.kinoplasticpremios.Login.SignUp

data class BrandListResponse(
    val code: Int,
    val `data`: List<BrandResponseData>,
    val message: String,
    val success: Boolean
)