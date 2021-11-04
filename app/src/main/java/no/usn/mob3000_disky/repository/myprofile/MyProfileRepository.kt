package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User

interface MyProfileRepository {

    suspend fun getProfileFeed(user: User): List<Post>

    suspend fun createFeedPost(post: Post): Post

}