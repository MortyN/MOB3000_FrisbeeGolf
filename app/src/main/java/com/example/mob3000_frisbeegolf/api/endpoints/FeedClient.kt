package com.example.mob3000_frisbeegolf.api.endpoints

import com.example.mob3000_frisbeegolf.api.filter.PostFilter
import com.example.mob3000_frisbeegolf.api.model.User
import com.example.mob3000_frisbeegolf.model.Posttest
import okhttp3.MultipartBody
import okhttp3.RequestBody


import retrofit2.Call
import retrofit2.http.*

interface FeedClient {

    @POST("get")
    fun user(@Body user: PostFilter): Call<List<Posttest>>

//    @Multipart
//    @POST("create")
//    fun user(@Part user: MultipartBody.Part, @Part image: MultipartBody.Part): Call<User>


    @Multipart
    @POST("create")
    fun createUser(
        @Part("user") user: User,
        @Part image: MultipartBody.Part): Call<User>
}