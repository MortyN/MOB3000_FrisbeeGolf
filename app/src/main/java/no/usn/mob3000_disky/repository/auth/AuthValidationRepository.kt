package no.usn.mob3000_disky.repository.auth

import no.usn.mob3000_disky.model.User
import retrofit2.Call

interface AuthValidationRepository {
    suspend fun validategso(id_token: String): User
}