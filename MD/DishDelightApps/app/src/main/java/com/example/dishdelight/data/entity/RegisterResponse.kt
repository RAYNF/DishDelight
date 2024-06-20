package com.example.dishdelight.data.entity

import com.google.gson.annotations.SerializedName

//register
data class RegisterResponse(
    @field:SerializedName("message")
    val message: String
)

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

//login
data class LoginResponse(
    @field:SerializedName("token")
    val token: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

//rekomendasi menu
data class RecomendationResponse(

    @field:SerializedName("recommendations")
    val recommendations: List<RecommendationsItem>
)

data class RecommendationsItem(

    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("menu_name")
    val menuName: String,

    @field:SerializedName("menu_rating")
    val menuRating: Int
)


