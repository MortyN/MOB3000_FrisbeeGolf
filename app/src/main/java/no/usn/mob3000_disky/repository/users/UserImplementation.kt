package no.usn.mob3000_disky.repository.users

import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.endpoints.UserAPIService
import no.usn.mob3000_disky.model.*

class UserImplementation
    constructor(
        private val userAPI: UserAPIService
    ): UserRepository {


    override suspend fun getUserList(user: UserFilter): List<User> {
        return userAPI.getUserList(user)
    }

    override suspend fun toggleFriend(toggleWrapper: ToggleWrapper): UserLink {
        return userAPI.toggleFriend(toggleWrapper)
    }

}