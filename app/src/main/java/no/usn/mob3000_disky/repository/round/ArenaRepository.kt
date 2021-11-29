package no.usn.mob3000_disky.repository.round

import no.usn.mob3000_disky.model.*
import okhttp3.ResponseBody

interface ArenaRepository {

    suspend fun getArena(arena: ArenaFilter, idToken: String): List<Arena>

    suspend fun createArena(arena: Arena, idToken: String): Arena

}