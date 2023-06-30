package com.example.sample13l09permissions

import retrofit2.Call
import retrofit2.http.GET

interface GithubInterface {

    @GET("users")
    fun getUsers(): Call<List<User>>
}