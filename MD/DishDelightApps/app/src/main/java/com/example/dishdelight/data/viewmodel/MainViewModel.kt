package com.example.dishdelight.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdelight.data.api.ApiConfig
import com.example.dishdelight.data.entity.LoginRequest
import com.example.dishdelight.data.entity.LoginResponse
import com.example.dishdelight.data.entity.RecomendationResponse
import com.example.dishdelight.data.entity.RecommendationsItem
import com.example.dishdelight.data.entity.RegisterRequest
import com.example.dishdelight.data.entity.RegisterResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    private val _listRecomendation = MutableLiveData<List<RecommendationsItem>?>()
    val listRecomendation: LiveData<List<RecommendationsItem>?> = _listRecomendation

    fun registerUser(username: String, email: String, password: String) {
        _loading.value = true
        val registerRequest = RegisterRequest(username, email, password)
        val client = ApiConfig.getApiService().register(registerRequest)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("Cek Api", "koneksi respon register berhasil")
                //error di sini
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _message.value = responseBody.message
                        _error.value = false
                        Log.d("proses register", response.message())
                    }
                } else {
                    Log.d("proses register", "sudah respon tapi gagal: ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = JSONObject(errorBody).getString("message")
                    _loading.value = false
                    _message.value = errorMessage
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _error.value = true
                Log.e("Koneksi Api", "gagal koneksi: ${t.message.toString()}")
            }

        })
    }

    fun loginUser(email: String, password: String) {
        _loading.value = true
        val loginRequest = LoginRequest(email, password)
        val client = ApiConfig.getApiService().login(loginRequest)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    Log.d("Cek Api", "koneksi respon login berhasil")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _message.value = "Login berhasil"
                        _error.value = false
                        _token.value = responseBody.token
                        Log.d("koneksi Api", "Login aman")
                    }
                } else {
                    Log.d("Cek Api", "sudah respon loghin tapi gagal: ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = JSONObject(errorBody).getString("message")
                    _loading.value = false
                    _message.value = errorMessage
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _error.value = true
                Log.d("Cek Api", "koneksi respon login gagal")
            }

        })
    }

    fun AllRecomendation(token:String){
        _loading.value = false
        val client = ApiConfig.getApiService().getRecomendation("Bearer $token")
        client.enqueue(object : Callback<RecomendationResponse>{
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
               if (response.isSuccessful){
                   Log.d("Cek Api", "koneksi respon get all rekomendasi berhasil")
                   val responseBody = response.body()
                   if (responseBody != null){
                       _loading.value = false
                       _error.value = false
                       Log.d("Koneksi Api", "rekomendasi aman")
                       _listRecomendation.value = responseBody.recommendations
                   }
               }else{
                   Log.d("Cek Api", "koneksi respon get all rekomendasi gagal")
                   _loading.value = false
                   _message.value = "belum ambil data"
                   _error.value = true
               }
            }

            override fun onFailure(call: Call<RecomendationResponse>, t: Throwable) {
                Log.d("Cek Api", "respon rekomedasi gagal")
                _error.value = true
            }

        })
    }

}