package com.example.dishdelight.di

import android.content.Context
import com.example.dishdelight.data.pref.UserPreference
import com.example.dishdelight.data.pref.dataStore
import com.example.dishdelight.data.repository.UserRepository

object Injection {
    fun provideRepository(context: Context) : UserRepository{
        val pref = UserPreference.getInstance(context.dataStore)
        return UserRepository.getInstance(pref)
    }
}