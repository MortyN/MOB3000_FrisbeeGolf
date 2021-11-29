package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

class PostImplementation
    constructor(
        private val postAPI: PostAPIService
    ): PostRepository {

    override suspend fun getFeed(user: PostFilter, idToken: String): List<Post> {
        return postAPI.getProfileFeed(user = user, idToken = idToken)
    }

    override suspend fun createPost(post: Post, idToken: String): Post {
        return postAPI.createFeedPost(post = post, idToken = idToken)
    }

    override suspend fun deletePost(postId: Int, idToken: String): ResponseBody {
        return postAPI.deleteFeedPost(postId = postId, idToken = idToken)
    }

    override suspend fun interactPost(interaction: Interaction, idToken: String): Interaction{
        return postAPI.interactPost(interaction = interaction, idToken = idToken)
    }

}