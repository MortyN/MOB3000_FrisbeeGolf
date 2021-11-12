package no.usn.mob3000_disky.repository.auth

import no.usn.mob3000_disky.endpoints.AuthAPIService
import no.usn.mob3000_disky.model.User

class AuthImplementation
    constructor(
        private val authAPI: AuthAPIService
    ): AuthRepository{

    override suspend fun getOneUser(userId: Int): User {
        return authAPI.getOneUser(userId)
    }

}