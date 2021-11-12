package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody
import retrofit2.http.*

interface PostAPIService {

    @POST("get")
    suspend fun getProfileFeed(@Body user: PostFilter): List<Post>

    @POST("create")
    suspend fun createFeedPost(@Body post: Post): Post

    @DELETE("delete/{postId}")
    suspend fun deleteFeedPost(@Path("postId") postId: Int): ResponseBody

    @POST("interact")
    suspend fun interactPost(@Body interaction: Interaction): Interaction

}