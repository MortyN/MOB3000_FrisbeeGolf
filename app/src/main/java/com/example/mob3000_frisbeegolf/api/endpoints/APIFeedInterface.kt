package com.example.mob3000_frisbeegolf.api.endpoints


import com.example.mob3000_frisbeegolf.api.constants.ApiConstants
import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface APIFeedInterface {

    @POST("get")
    fun getFeed(@Body user: PostFilterByUser): Call<List<PostResponse>>

    @POST("get")
    suspend fun getProfileFeed(@Body user: PostFilterByUser): List<PostResponse>

    companion object {
        var apiService: APIFeedInterface? = null
        fun getInstance() : APIFeedInterface {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl(ApiConstants.APIHOST + ApiConstants.APIPORT + ApiConstants.APIPOSTPREFIX)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(APIFeedInterface::class.java)
            }
            return apiService!!
        }
    }
}