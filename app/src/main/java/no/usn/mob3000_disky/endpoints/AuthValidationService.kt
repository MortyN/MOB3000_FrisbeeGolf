package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.User
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthValidationService {
    @POST("/auth/validategso/id_token={id_token}")
    fun validategso(@Path("id_token") id_token: String): User
}