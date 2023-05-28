package com.example.lifemate.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("idUser")
    val idUser: Int,

    @field:SerializedName("token")
    val token: String
)
