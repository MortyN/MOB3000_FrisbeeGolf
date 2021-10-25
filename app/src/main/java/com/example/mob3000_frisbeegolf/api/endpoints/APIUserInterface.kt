package com.example.mob3000_frisbeegolf.api.endpoints

import com.example.mob3000_frisbeegolf.api.model.User
import okhttp3.MultipartBody

import retrofit2.Call
import retrofit2.http.*

interface APIUserInterface {
    @Multipart
    @POST("create")
    fun createUser(
        @Part("user") user: User,
        @Part image: MultipartBody.Part): Call<User>

}