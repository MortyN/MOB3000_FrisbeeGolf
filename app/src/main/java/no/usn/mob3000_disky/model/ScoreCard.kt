package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class ScoreCard(
    @SerializedName("cardId") var cardId : Long = 0,
    @SerializedName("arenaRound") var arenaRound : ArenaRound = ArenaRound(),
    @SerializedName("startTs") var startTs : String = "",
    @SerializedName("endTs") var endTs : String = "",
    @SerializedName("createdBy") var createdBy : User = User(0),
    @SerializedName("members") var members : List<ScoreCardMember> = ArrayList(),
)

data class ScoreCardFilter(
    @SerializedName("member") var user : User,
    @SerializedName("scoreCardId") var scoreCardId : Long
)