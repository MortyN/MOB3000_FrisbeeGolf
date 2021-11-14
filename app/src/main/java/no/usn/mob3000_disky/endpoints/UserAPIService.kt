package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.model.UserFilter
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPIService {

    @POST("get")
    suspend fun getUserList(@Body user: UserFilter): List<User>

}