package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class ScoreCard(
    @SerializedName("cardId") var cardId : Long?,
    @SerializedName("arenaRound") var arenaRound : ArenaRound?,
    @SerializedName("startTs") var startTs : String,
    @SerializedName("endTs") var endTs : String,
    @SerializedName("createdBy") var createdBy : User?,
    @SerializedName("members") var members : List<ScoreCardMember>?,
)

data class ScoreCardFilter(
    @SerializedName("member") var user : User
)