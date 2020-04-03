package com.diggytech.kinoplasticpremios.DashBoard

data class ProfileResponseData(
    val cpf_number: String,
    val email: String,
    val id: String,
    val phone_number: String,
    val profile_pic: String,
    val status: Int,
    val user_Location: List<ProfileResponseUserLocation>,
    val user_type: String,
    val username: String
)