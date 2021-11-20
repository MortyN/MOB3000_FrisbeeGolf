package no.usn.mob3000_disky.repository.round

import no.usn.mob3000_disky.endpoints.ArenaAPIService
import no.usn.mob3000_disky.model.*

class ArenaImplementation
    constructor(
        private val arenaAPI: ArenaAPIService
    ): ArenaRepository {

    override suspend fun getArena(arena: ArenaFilter): List<Arena> {
        return arenaAPI.getArena(arena = arena)
    }

}