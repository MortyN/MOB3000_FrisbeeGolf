package com.example.mob3000_frisbeegolf.api.filter

import com.example.mob3000_frisbeegolf.api.model.ScoreCard
import com.example.mob3000_frisbeegolf.api.model.User


data class PostFilter(
    val user: User,
    val type: Int,
    val scoreCardId: ScoreCard,
    val getFromConnections: Boolean
)

data class PostFilterByUser(
    val user: User,
    val type: Long?,
    val scoreCardId: ScoreCard?,
    val getFromConnections: Boolean
)

//, val type: Int?, val scoreCardId: ScoreCard?, val getFromConnections: Boolean