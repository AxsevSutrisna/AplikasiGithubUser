package com.asepssp.aplikasiusergithub.data.dataapi.apiresponse

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<UserResponseItems>
)

data class UserResponseItems(

    @field:SerializedName("login")
    val login: String,


    @field:SerializedName("avatar_url")
    val avatarUrl: String
)