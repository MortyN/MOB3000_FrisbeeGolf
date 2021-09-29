package com.example.mob3000_frisbeegolf.model

import java.util.*

data class Arena(
    val arenaId: Long,
    val name: String,
    val desc: String,
    val established: Date,
    val createdBy: Long,
    val createdTs: Date,
    val updateTs: Date
)
