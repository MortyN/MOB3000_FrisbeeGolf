package com.example.mob3000_frisbeegolf.activities.Settings

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface UploadImage {

    @Multipart
    @POST("upload/chatImage")
    fun uploadChatImage(@Part file: MultipartBody.Part)

    companion object{
        operator fun invoke() : UploadImage {
            return Retrofit.Builder()
                .baseUrl("http://192.168.50.240")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UploadImage::class.java)
        }
    }
}