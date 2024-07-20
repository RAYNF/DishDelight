package com.example.dishdelight.data.remote.api

import com.example.dishdelight.data.remote.entity.AddFavoriteResponse
import com.example.dishdelight.data.remote.entity.AddMenuRequest
import com.example.dishdelight.data.remote.entity.AddMenuResponse
import com.example.dishdelight.data.remote.entity.DetailResponse
import com.example.dishdelight.data.remote.entity.ListFavoriteResponse
import com.example.dishdelight.data.remote.entity.LoginRequest
import com.example.dishdelight.data.remote.entity.LoginResponse
import com.example.dishdelight.data.remote.entity.RecomendationResponse
import com.example.dishdelight.data.remote.entity.RegisterRequest
import com.example.dishdelight.data.remote.entity.RegisterResponse
import com.example.dishdelight.data.remote.entity.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("auth/menu/{id}")
    fun getDetail(
        @Path("id") id: Int,
        @Header("Authorization")token:String
    ): Call<DetailResponse>

    @GET("auth/menu/search")
    fun searchRecipe(
        @Header("Authorization")token:String,
        @Query("q") query: String
    ):Call<SearchResponse>


    //addFavorite
    @POST("auth/menu/{id}/favorite")
    fun addFavorite(
        @Path("id") id: Int,
        @Header("Authorization")token:String
    ): Call<AddFavoriteResponse>

    //show listFavorite
    @GET("auth/favorite_menus")
    fun getListFavorite(
        @Header("Authorization")token:String
    ):Call<ListFavoriteResponse>


    //add menu
    @POST("auth/menu/add")
    fun addMenu(
        @Body request: AddMenuRequest,
        @Header("Authorization")token:String
    ): Call<AddMenuResponse>

}