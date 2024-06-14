package com.example.dishdelight.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.dishdelight.data.pref.UserModel
import com.example.dishdelight.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModelMain(private val repository: UserRepository) : ViewModel() {

    fun getSession():LiveData<UserModel>{
        return repository.getSession().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            repository.logout()
        }
    }
}