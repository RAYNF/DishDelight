package com.example.dishdelight.data.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val selectedMenuId = MutableLiveData<Int>()
}