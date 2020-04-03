package com.diggytech.kinoplasticpremios.Login.SignUp

data class UploadImageResponse(
    val code: Int,
    val success: Boolean,
       val message: String,
       val `data`: List<Any>
)