package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

interface PostRepository {

    suspend fun getFeed(user: PostFilter): List<Post>

    suspend fun createPost(post: Post): Post

    suspend fun deletePost(postId: Int): ResponseBody

    suspend fun interactPost(interaction: Interaction): Interaction



}