package no.usn.mob3000_disky.api.filter

import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.User

data class PostFilterByUser(
    val user: User,
    val type: Long?,
    val scoreCardId: ScoreCard?,
    val getFromConnections: Boolean
)