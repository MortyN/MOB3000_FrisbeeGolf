package no.usn.mob3000_disky.endpoints

import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import retrofit2.http.Body
import retrofit2.http.POST

interface ArenaAPIService {

    @POST("get")
    suspend fun getArena(@Body arena: ArenaFilter): List<Arena>

}