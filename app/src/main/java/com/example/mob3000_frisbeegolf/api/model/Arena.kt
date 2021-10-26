package com.example.mob3000_frisbeegolf.api.model

import java.util.*

class Arena {
    private var arenaId: Long
    private var arenaName: String? = null
    private var description: String? = null
    private var established: Int? = null
    private var createdBy: User? = null
    private var createdTs: Date? = null
    private var updateTs: Date? = null
    private var active = false

    constructor(arenaId: Long) {
        this.arenaId = arenaId
    }

    constructor(
        arenaId: Long,
        arenaName: String?,
        description: String?,
        established: Int?,
        createdBy: User?,
        createdTs: Date?,
        updateTs: Date?,
        active: Boolean
    ) {
        this.arenaId = arenaId
        this.arenaName = arenaName
        this.description = description
        this.established = established
        this.createdBy = createdBy
        this.createdTs = createdTs
        this.updateTs = updateTs
        this.active = active
    }
}