package no.usn.mob3000_disky.repository.users

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

interface UserRepository {

    suspend fun getUserList(user: UserFilter): List<User>

    suspend fun toggleFriend(toggleWrapper: ToggleWrapper): UserLink

}