package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.PostFilter
import no.usn.mob3000_disky.model.UserLink
import no.usn.mob3000_disky.model.UserLinkFilter
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface FriendsAPIService {
    @POST("getLinks")
    suspend fun getFriends(@Body filter: UserLinkFilter, @Header("token") idToken: String): List<UserLink>

    @POST("update")
    suspend fun updateFriend(@Body userLink: UserLink, @Header("token") idToken: String): UserLink
}