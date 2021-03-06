package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class ScoreCardResult(
    @SerializedName("scoreCardMember") var scoreCardMember : ScoreCardMember = ScoreCardMember(),
    @SerializedName("arenaRoundHole") var arenaRoundHole : ArenaRoundHole = ArenaRoundHole(),
    @SerializedName("scoreValue") var scoreValue : Int = 0
)