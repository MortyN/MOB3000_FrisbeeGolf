package no.usn.mob3000_disky.repository.myprofile

import no.usn.mob3000_disky.endpoints.MyProfileAPIService
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User

class MyProfile_Impl
    constructor(
        private val myProfileAPI: MyProfileAPIService
    ): MyProfileRepository {

    override suspend fun getProfileFeed(user: User): List<Post> {
        return myProfileAPI.getProfileFeed(user = user)
    }

    override suspend fun createFeedPost(post: Post): Post {
        return myProfileAPI.createFeedPost(post = post)
    }


}