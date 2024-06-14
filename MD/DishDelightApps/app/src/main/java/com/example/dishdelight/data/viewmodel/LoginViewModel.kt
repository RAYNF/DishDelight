package com.example.dishdelight.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.pref.UserModel
import com.example.dishdelight.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository):ViewModel() {
    fun saveSession(user:UserModel){
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}