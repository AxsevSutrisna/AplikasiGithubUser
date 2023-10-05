package com.asepssp.aplikasiusergithub.ui.detail.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import com.asepssp.aplikasiusergithub.data.dataapi.retrofitconfig.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {

    companion object {
        const val TAG = "DetailViewModel"
    }


    private val _listFollower = MutableLiveData<List<UserResponseItems>>()
    val listFollower: LiveData<List<UserResponseItems>> = _listFollower

    private val _listFollowing = MutableLiveData<List<UserResponseItems>>()
    val listFollowing: LiveData<List<UserResponseItems>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun userFollowing(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userFollowing(name)
        client.enqueue(object : Callback<List<UserResponseItems>> {
            override fun onResponse(
                call: Call<List<UserResponseItems>>,
                response: Response<List<UserResponseItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollowing.value = response.body()
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


    fun userFollowers(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userFollower(name)
        client.enqueue(object : Callback<List<UserResponseItems>> {
            override fun onResponse(
                call: Call<List<UserResponseItems>>,
                response: Response<List<UserResponseItems>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listFollower.value = response.body()
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

}
