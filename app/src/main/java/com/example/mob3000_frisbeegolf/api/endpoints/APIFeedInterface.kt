package com.example.mob3000_frisbeegolf.api.endpoints


import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIFeedInterface {

    @POST("get")
    fun getFeed(@Body user: PostFilterByUser): Call<List<PostResponse>>

}