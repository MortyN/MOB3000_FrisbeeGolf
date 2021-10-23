package com.example.mob3000_frisbeegolf.api.endpoints

import com.example.mob3000_frisbeegolf.api.filter.PostFilter
import com.example.mob3000_frisbeegolf.model.Usertest


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface FeedClient {

    @POST("get")
    fun user(@Body user: PostFilter): Call<List<Usertest>>

}