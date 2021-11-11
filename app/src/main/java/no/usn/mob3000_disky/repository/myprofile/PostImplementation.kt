package no.usn.mob3000_disky.repository.myprofile

import android.util.Log
import android.widget.Toast
import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostImplementation
    constructor(
        private val postAPI: PostAPIService
    ): PostRepository {

    override suspend fun getFeed(user: PostFilter): List<Post> {
        return postAPI.getProfileFeed(user = user)
    }

    override suspend fun createPost(post: Post): Post {
        return postAPI.createFeedPost(post = post)
    }

    override suspend fun deletePost(postid: Long): ResponseBody {
        return postAPI.deleteFeedPost(postId = postid)
    }

    override suspend fun interactPost(interaction: Interaction): Interaction{
        return postAPI.interactPost(interaction = interaction)
    }

}