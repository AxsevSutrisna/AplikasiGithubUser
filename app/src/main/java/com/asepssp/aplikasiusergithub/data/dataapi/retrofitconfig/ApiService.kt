package com.asepssp.aplikasiusergithub.data.dataapi.retrofitconfig

import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponse
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseDetail
import com.asepssp.aplikasiusergithub.data.dataapi.apiresponse.UserResponseItems
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("users")
    fun userGithub(): Call<List<UserResponseItems>>

    @GET("search/users")
    fun searchUser(
        @Query("q") q: String
    ): Call<UserResponse>

    @GET("users/{name}")
    fun userDetail(
        @Path("name") name: String
    ): Call<UserResponseDetail>

    @GET("users/{name}/followers")
    fun userFollower(
        @Path("name") name: String
    ): Call<List<UserResponseItems>>

    @GET("users/{name}/following")
    fun userFollowing(
        @Path("name") name: String
    ): Call<List<UserResponseItems>>
}