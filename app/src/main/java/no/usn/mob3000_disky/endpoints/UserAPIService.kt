package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.*
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPIService {

    @POST("get")
    suspend fun getUserList(@Body user: UserFilter): List<User>

    @POST("toggle")
    suspend fun toggleFriend(@Body toggleWrapper: ToggleWrapper): UserLink

}