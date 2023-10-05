package com.asepssp.aplikasiusergithub.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseDetail
import com.asepssp.aplikasiusergithub.data.dataapi.retrofitconfig.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object {
        const val USERNAME = "Username"
        const val AVATAR_URL = "avatar_URL"
        const val URL = "url"
        const val TAG = "DetailViewModel"
    }


    private val _userDetail = MutableLiveData<UserResponseDetail>()
    val userDetail: LiveData<UserResponseDetail> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun usersDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().userDetail(username)
        client.enqueue(object : Callback<UserResponseDetail> {
            override fun onResponse(
                call: Call<UserResponseDetail>,
                response: Response<UserResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<UserResponseDetail>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
