package com.asepssp.aplikasiusergithub.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponse
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.data.dataapi.retrofitconfig.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }


    private val _userList = MutableLiveData<List<UserResponseItems>>()
    val userList: LiveData<List<UserResponseItems>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userSearch = MutableLiveData<List<UserResponseItems>>()
    val userSearch: LiveData<List<UserResponseItems>> = _userSearch


    fun usersList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userGithub()
        client.enqueue(object : Callback<List<UserResponseItems>> {
            override fun onResponse(
                call: Call<List<UserResponseItems>>,
                response: Response<List<UserResponseItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userList.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItems>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }


    fun usersSearch(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userSearch.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, t.message.toString())
            }
        })
    }
}
