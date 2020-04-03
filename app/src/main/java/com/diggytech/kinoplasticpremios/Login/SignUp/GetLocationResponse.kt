package com.diggytech.kinoplasticpremios.Login.SignUp

data class GetLocationResponse(
    var `data`: List<Data>,
    var code: Int, // 200
    var message: String, // Location Found Successfully
    var success: Boolean // true
)