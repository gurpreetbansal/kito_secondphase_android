package com.diggytech.kinoplasticpremios.Login.SignUp

data class StateListResponse(
    val code: Int,
    val `data`: List<StateListData>,
    val message: String,
    val success: Boolean
)