package com.example.dishdelight.data.entity

import com.google.gson.annotations.SerializedName

data class DetailResponse(

	@field:SerializedName("menu_detail")
	val menuDetail: MenuDetail? = null
)

data class MenuDetail(

	@field:SerializedName("author_name")
	val authorName: String? = null,

	@field:SerializedName("instructions")
	val instructions: List<String?>? = null,

	@field:SerializedName("is_favorite")
	val isFavorite: Boolean? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("menu_name")
	val menuName: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: List<String?>? = null,

	@field:SerializedName("author_id")
	val authorId: Int? = null,

	@field:SerializedName("menu_rating")
	val menuRating: Any? = null
)
