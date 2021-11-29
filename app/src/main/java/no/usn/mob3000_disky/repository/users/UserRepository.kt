package no.usn.mob3000_disky.repository.users

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

interface UserRepository {

    suspend fun getUserList(user: UserFilter, idToken: String): List<User>

    suspend fun toggleFriend(toggleWrapper: ToggleWrapper, idToken: String): UserLink

}