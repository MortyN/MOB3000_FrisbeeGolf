package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("postId") var postId : Int?,
    @SerializedName("user") var user : User,
    @SerializedName("message") var message : String,
    @SerializedName("type") var type : Int,
    @SerializedName("scoreCard") var scoreCard : ScoreCard?,
    @SerializedName("postedTs") var postedTs : String,
    @SerializedName("updatedTs") var updatedTs : String,

    )
