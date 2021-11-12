package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

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

    override suspend fun deletePost(postId: Int): ResponseBody {
        return postAPI.deleteFeedPost(postId = postId)
    }

    override suspend fun interactPost(interaction: Interaction): Interaction{
        return postAPI.interactPost(interaction = interaction)
    }

}