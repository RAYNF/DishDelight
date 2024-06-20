package com.example.dishdelight.data.api

import com.example.dishdelight.data.entity.LoginRequest
import com.example.dishdelight.data.entity.LoginResponse
import com.example.dishdelight.data.entity.RecomendationResponse
import com.example.dishdelight.data.entity.RegisterRequest
import com.example.dishdelight.data.entity.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("auth/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>


    @GET("homepage/homepage")
    fun getRecomendation(
        @Header("Authorization")token:String
    ):Call<RecomendationResponse>
}