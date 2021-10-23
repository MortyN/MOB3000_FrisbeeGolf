package com.example.mob3000_frisbeegolf.api.model

class ArenaRoundHole {
    private var arenaRoundHoleId: Long
    private var arenaRound: ArenaRound? = null
    private var holeName: String? = null
    private var parValue: Int? = null
    private var active: Boolean? = null
    private val latitude: String? = null
    private val longitude: String? = null

    constructor(arenaRoundHoleId: Long) {
        this.arenaRoundHoleId = arenaRoundHoleId
    }

    constructor(
        arenaRoundHoleId: Long,
        arenaRound: ArenaRound?,
        holeName: String?,
        parValue: Int?,
        active: Boolean?
    ) {
        this.arenaRoundHoleId = arenaRoundHoleId
        this.arenaRound = arenaRound
        this.holeName = holeName
        this.parValue = parValue
        this.active = active
    }
}