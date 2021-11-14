package no.usn.mob3000_disky.model

import java.util.*

class ArenaRound (
            val arenaRoundId: Long?,
            val arena: Arena,
            val holeAmount: Int?,
            val payment: Boolean?,
            val description: String?,
            val createdBy: User?,
            val creationTs: Date?,
            val updateTs: Date?
        )