package com.example.dishdelight.data.remote.entity

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


//detail response

data class DetailResponse(

    @field:SerializedName("menu_detail")
    val menuDetail: MenuDetail
)

data class MenuDetail(

    @field:SerializedName("author_name")
    val authorName: String,

    @field:SerializedName("instructions")
    val instructions: List<String>,

    @field:SerializedName("is_favorite")
    val isFavorite: Boolean,

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("menu_name")
    val menuName: String,

    @field:SerializedName("description")
    val description: String,

    @field:SerializedName("ingredients")
    val ingredients: List<String>,

    @field:SerializedName("author_id")
    val authorId: Int,

    @field:SerializedName("menu_rating")
    val menuRating: Any
)

//search
data class SearchResponse(

    @field:SerializedName("search_results")
    val searchResults: List<SearchResultsItem>
)

data class SearchResultsItem(

    @field:SerializedName("image_url")
    val imageUrl: String,

    @field:SerializedName("menu_name")
    val menuName: String
)