package no.usn.mob3000_disky.repository.auth

import no.usn.mob3000_disky.endpoints.AuthValidationService
import no.usn.mob3000_disky.model.User
import retrofit2.Call

class AuthValidationImplementation constructor(
    private val authValidationApi: AuthValidationService
): AuthValidationRepository {
    override suspend fun validategso(id_token: String): User {
        return authValidationApi.validategso(id_token)
    }
}