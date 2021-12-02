package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

interface PostRepository {

    suspend fun getFeed(user: PostFilter, idToken: String): List<Post>

    suspend fun createPost(post: Post, idToken: String): Post

    suspend fun deletePost(postId: Int, idToken: String): ResponseBody

    suspend fun interactPost(interaction: Interaction, idToken: String): Interaction
}