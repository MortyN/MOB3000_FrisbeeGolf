package no.usn.mob3000_disky.repository.auth

import no.usn.mob3000_disky.model.User

interface AuthRepository{
        suspend fun getOneUser(userId: Int): User
    }

