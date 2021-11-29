package no.usn.mob3000_disky.repository.round

import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.model.*

class ArenaImplementation
    constructor(
        private val arenaAPI: ArenaAPIService
    ): ArenaRepository {

    override suspend fun getArena(arena: ArenaFilter, idToken: String): List<Arena> {
        return arenaAPI.getArena(arena = arena, idToken)
    }

    override suspend fun createArena(arena: Arena, idToken: String): Arena {
        return arenaAPI.createArena(arena = arena, idToken)
    }

}