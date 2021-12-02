package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthValidationService {
    @POST("/auth/validategso")
    suspend fun validategso(@Query("id_token") id_token: String): User

    @GET("/auth/getTestUser")
    suspend fun getTestUser(@Query("userId") userId: Long): User
}