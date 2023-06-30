package com.example.sample13l09permissions

import com.google.gson.annotations.SerializedName

data class User(
    val login: String,
    @SerializedName("site_admin")
    val siteAdmin: Boolean,
    @SerializedName("avatar_url")
    val avatarUrl: String
)