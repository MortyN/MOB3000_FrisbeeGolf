package no.usn.mob3000_disky.repository.users

import no.usn.mob3000_disky.endpoints.UserAPIService
import no.usn.mob3000_disky.model.ToggleWrapper
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.model.UserFilter
import no.usn.mob3000_disky.model.UserLink
import okhttp3.MultipartBody
import retrofit2.http.Header

class UserImplementation
    constructor(
        private val userAPI: UserAPIService
    ): UserRepository {


    override suspend fun getUserList(user: UserFilter, idToken: String): List<User> {
        return userAPI.getUserList(user, idToken)
    }

    override suspend fun toggleFriend(toggleWrapper: ToggleWrapper, idToken: String): UserLink {
        return userAPI.toggleFriend(toggleWrapper, idToken)
    }

    override suspend fun updateUser(user: User, image: MultipartBody.Part, idToken: String): User {
        return userAPI.updateUser(user, image, idToken)
    }

    override suspend fun updateUserNoImage(user: User, idToken: String): User {
        return userAPI.updateUserNoImage(user, idToken)
    }

}