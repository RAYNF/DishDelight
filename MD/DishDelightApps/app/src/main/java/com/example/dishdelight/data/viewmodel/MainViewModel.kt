package com.example.dishdelight.data.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dishdelight.data.remote.api.ApiConfig
import com.example.dishdelight.data.remote.entity.AddFavoriteResponse
import com.example.dishdelight.data.remote.entity.AddMenuRequest
import com.example.dishdelight.data.remote.entity.AddMenuResponse
import com.example.dishdelight.data.remote.entity.DetailResponse
import com.example.dishdelight.data.remote.entity.FavoriteMenusItem
import com.example.dishdelight.data.remote.entity.ListFavoriteResponse
import com.example.dishdelight.data.remote.entity.LoginRequest
import com.example.dishdelight.data.remote.entity.LoginResponse
import com.example.dishdelight.data.remote.entity.MenuDetail
import com.example.dishdelight.data.remote.entity.RecomendationResponse
import com.example.dishdelight.data.remote.entity.RecommendationsItem
import com.example.dishdelight.data.remote.entity.RegisterRequest
import com.example.dishdelight.data.remote.entity.RegisterResponse
import com.example.dishdelight.data.remote.entity.SearchResponse
import com.example.dishdelight.data.remote.entity.SearchResultsItem
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

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

    private val _detailRecipe = MutableLiveData<MenuDetail>()
    val detailRecipe: LiveData<MenuDetail> = _detailRecipe

    private val _listRecomendationSearch = MutableLiveData<List<SearchResultsItem>?>()
    val listRecomendationSearch: LiveData<List<SearchResultsItem>?> = _listRecomendationSearch

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> = _favorite

    private val _listFavorite = MutableLiveData<List<FavoriteMenusItem>?>()
    val listFavorite: LiveData<List<FavoriteMenusItem>?> = _listFavorite

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

    fun AllRecomendation(token: String) {
        _loading.value = false
        val client = ApiConfig.getApiService().getRecomendation("Bearer $token")
        client.enqueue(object : Callback<RecomendationResponse> {
            override fun onResponse(
                call: Call<RecomendationResponse>,
                response: Response<RecomendationResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Cek Api", "koneksi respon get all rekomendasi berhasil")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _error.value = false
                        Log.d("Koneksi Api", "rekomendasi aman")
                        _listRecomendation.value = responseBody.recommendations
                    }
                } else {
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

    fun DetailRecipe(id: Int, token: String) {
        _loading.value = false
        val client = ApiConfig.getApiService().getDetail(id, "Bearer $token")
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _error.value = false
                        Log.d("Koneksi Api", "detail RECIPE aman")
                        _detailRecipe.value = responseBody.menuDetail
                    }
                } else {

                    Log.e(
                        "Koneksi Api",
                        "sudah respon detail stories tapi gagal: ${response.message()}"
                    )
                    Log.e("DetailStory", "token: $token")
                    _loading.value = false
                    _message.value = "gagal amabil detail"
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _loading.value = false
                Log.d("Cek Api", "detail recipe gagal")
                _error.value = true
            }

        })

    }

    fun searchRecipe(token: String, query: String) {
        _loading.value = true
        val client = ApiConfig.getApiService().searchRecipe("Bearer $token", query)
        client.enqueue(object : Callback<SearchResponse> {
            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful) {
                    _loading.value = false
                    _error.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listRecomendationSearch.value = responseBody.searchResults
                        Log.d("searchRecipe", "Data found: ${responseBody.searchResults}")
                    } else {
                        _error.value = true
                        Log.e("searchRecipe", "Empty response body")
                    }
                } else {
                    _loading.value = false
                    _error.value = true
                    Log.e("searchRecipe", "Request failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                _loading.value = false
                _error.value = true
                Log.e("searchRecipe", "Network failure: ${t.message}")
            }
        })
    }

    //addfavorite
    fun AddFavorite(id: Int, token: String) {
        _loading.value = false
        val client = ApiConfig.getApiService().addFavorite(id, "Bearer $token")
        client.enqueue(object : Callback<AddFavoriteResponse> {
            override fun onResponse(
                call: Call<AddFavoriteResponse>,
                response: Response<AddFavoriteResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _error.value = false
                        _favorite.value = true
                        Log.d("Koneksi Api", "favorite RECIPE aman")
                        _message.value = responseBody.message
                    }
                } else {

                    Log.e(
                        "Koneksi Api",
                        "sudah respon detail stories tapi gagal: ${response.message()}"
                    )
                    Log.e("DetailStory", "token: $token")
                    _loading.value = false
                    _message.value = "gagal amabil detail"
                    _error.value = true
                    _favorite.value = false
                }
            }

            override fun onFailure(call: Call<AddFavoriteResponse>, t: Throwable) {
                _loading.value = false
                Log.d("Cek Api", "detail recipe gagal")
                _error.value = true
                _favorite.value = false
            }

        })

    }

    fun ListFavorite(token: String) {
        _loading.value = false
        val client = ApiConfig.getApiService().getListFavorite("Bearer $token")
        client.enqueue(object : Callback<ListFavoriteResponse> {
            override fun onResponse(
                call: Call<ListFavoriteResponse>,
                response: Response<ListFavoriteResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("Cek Api", "koneksi respon get all favorite berhasil")
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _error.value = false
                        Log.d("Koneksi Api", "favorite aman")
                        _listFavorite.value = responseBody.favoriteMenus
                    }
                } else {
                    Log.d("Cek Api", "koneksi respon get all favorite gagal")
                    _loading.value = false
                    _message.value = "belum ambil data"
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<ListFavoriteResponse>, t: Throwable) {
                Log.d("Cek Api", "respon favorite gagal")
                _error.value = true
            }

        })
    }

    fun addMenus(menu_name: String, image_url: String, description: String,ingredient:List<String>,instruction:List<String>,category:String,token: String) {
        _loading.value = true
        val menuRequest = AddMenuRequest(menu_name,image_url,description,ingredient,instruction,category)
        val client = ApiConfig.getApiService().addMenu(menuRequest,"Bearer $token")
        client.enqueue(object : Callback<AddMenuResponse> {
            override fun onResponse(
                call: Call<AddMenuResponse>,
                response: Response<AddMenuResponse>
            ) {
                Log.d("Cek Api", "koneksi respon addMenu berhasil")
                //error di sini
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _loading.value = false
                        _message.value = responseBody.message
                        _error.value = false
                        Log.d("proses addMenu", response.message())
                    }
                } else {
                    Log.d("proses addMenu", "sudah respon addMenu tapi gagal: ${response.message()}")
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = JSONObject(errorBody).getString("message")
                    _loading.value = false
                    _message.value = errorMessage
                    _error.value = true
                }
            }

            override fun onFailure(call: Call<AddMenuResponse>, t: Throwable) {
                _error.value = true
                Log.e("Koneksi Api", "gagal koneksi addMenu: ${t.message.toString()}")
            }

        })
    }

}