package com.example.dishdelight.data.local.pref

data class UserModel(
    val email:String,
    val token:String,
    val password:String,
    val isLogin:Boolean = false

)
