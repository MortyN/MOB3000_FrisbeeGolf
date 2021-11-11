package no.usn.mob3000_disky.model

import java.util.*

//class ArenaRound {
//    private val arenaRoundId: Long? = null
//    private val arena: Arena? = null
//    private val holeAmount: Int? = null
//    private val payment: Boolean? = null
//    private val description: String? = null
//    private val createdBy: User? = null
//    private val creationTs: Date? = null
//    private val updateTs: Date? = null
//}

class ArenaRound (
            val arenaRoundId: Long?,
            val arena: Arena?,
            val holeAmount: Int?,
            val payment: Boolean?,
            val description: String?,
            val createdBy: User?,
            val creationTs: Date?,
            val updateTs: Date?
        )