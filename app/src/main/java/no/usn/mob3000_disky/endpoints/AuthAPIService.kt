package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.PostFilter
import no.usn.mob3000_disky.model.User
import retrofit2.http.*

interface AuthAPIService {
    @GET("getOne/{userId}")
    suspend fun getOneUser(@Path("userId") userId: Int): User
}