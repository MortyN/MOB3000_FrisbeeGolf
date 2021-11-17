package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class ScoreCardMember(
    @SerializedName("scoreCardMemberId") var scoreCardMemberId : Long = 0,
    @SerializedName("user") var user : User = User(0),
    @SerializedName("scoreCard") var scoreCard : ScoreCard = ScoreCard(),
    @SerializedName("results") var results : List<ScoreCardResult> = ArrayList(),
    @SerializedName("totalThrows") var totalThrows : Int = 0,
    @SerializedName("totalPar") var totalPar : Int = 0,
    @SerializedName("totalScore") var totalScore : Int = 0,
)