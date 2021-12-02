package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ArenaAPIService {

    @POST("get")
    suspend fun getArena(@Body arena: ArenaFilter, @Header("token") idToken: String): List<Arena>

    @POST("create")
    suspend fun createArena(@Body arena: Arena, @Header("token") idToken: String): Arena

}