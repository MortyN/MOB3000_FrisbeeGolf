package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.api.APIConstants
import no.usn.mob3000_disky.model.Interaction
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import okhttp3.ResponseBody

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface PostAPIService {

    @POST("get")
    suspend fun getProfileFeed(@Body user: User): List<Post>

    @POST("create")
    suspend fun createFeedPost(@Body post: Post): Post

    @POST("delete")
    suspend fun deleteFeedPost(@Body postId: Long): ResponseBody

    @POST("interact")
    suspend fun interactPost(@Body interaction: Interaction): Interaction

}