package com.example.lifemate.data.retrofit

import com.example.lifemate.data.response.LoginResponse
import com.example.lifemate.data.response.RegisterResponse
import retrofit2.http.*
import retrofit2.Call

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("birthDate") birthDate: String,
        @Field("gender") gender: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>
}