package no.usn.mob3000_disky.repository.users

import no.usn.mob3000_disky.model.*
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.Part

interface UserRepository {

    suspend fun getUserList(user: UserFilter, idToken: String): List<User>

    suspend fun toggleFriend(toggleWrapper: ToggleWrapper, idToken: String): UserLink

    suspend fun updateUser(user: User, image: MultipartBody.Part, idToken: String): User

}