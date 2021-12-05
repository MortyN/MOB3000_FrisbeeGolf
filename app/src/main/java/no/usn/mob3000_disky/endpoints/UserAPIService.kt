package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserAPIService {

    @POST("get")
    suspend fun getUserList(@Body user: UserFilter, @Header("token") idToken: String): List<User>

    @POST("toggle")
    suspend fun toggleFriend(@Body toggleWrapper: ToggleWrapper, @Header("token") idToken: String): UserLink

    @Multipart
    @POST("create")
    suspend fun updateUser(@Part("user") user: User, @Part image: MultipartBody.Part, @Header("token") idToken: String): User

    @Multipart
    @POST("create")
    suspend fun updateUserNoImage(@Part("user") user: User, @Header("token") idToken: String): User
}