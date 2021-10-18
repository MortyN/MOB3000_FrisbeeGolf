package com.example.mob3000_frisbeegolf.activities.Settings

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.*

public interface UploadClient{

//    @Multipart
//    @POST("uploads")
//    fun uploadsImage(
//        @Part("user_id") id: RequestBody?,
//        @Part("full_name") fullName: RequestBody?,
//        @Part image: MultipartBody.Part?,
//        @Part("other") other: RequestBody?
//    ): Observable?

    @Multipart
    @POST("upload")
    fun uploadsImage(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part
    ): Call<ResponseBody>
}