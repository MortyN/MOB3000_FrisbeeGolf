package no.usn.mob3000_disky.model

import java.util.*

class ScoreCard {
    private var cardId: Long
    private var arenaRound: ArenaRound? = null
    private var startTs: Date? = null
    private var endTs: Date? = null
    private var createdBy: User? = null

    constructor(cardId: Long) {
        this.cardId = cardId
    }

    constructor(
        cardId: Long,
        arenaRound: ArenaRound?,
        startTs: Date?,
        endTs: Date?,
        createdBy: User?
    ) {
        this.cardId = cardId
        this.arenaRound = arenaRound
        this.startTs = startTs
        this.endTs = endTs
        this.createdBy = createdBy
    }
}