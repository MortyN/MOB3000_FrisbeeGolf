package com.example.mob3000_frisbeegolf.api.model

import com.google.gson.annotations.SerializedName
import java.security.Timestamp

class Post {
    @SerializedName("postId")
    private var postId: Long
    @SerializedName("user")
    private var user: User? = null
    @SerializedName("message")
    private var message: String? = null
    @SerializedName("type")
    private var type = 0
    @SerializedName("scoreCard")
    private var scoreCard: ScoreCard? = null
    @SerializedName("postedTs")
    private var postedTs: Timestamp? = null
    @SerializedName("updatedTs")
    private var updatedTs: Timestamp? = null

    constructor(postId: Long) {
        this.postId = postId
    }

    constructor(
        postId: Long,
        user: User?,
        message: String?,
        type: Int,
        scoreCard: ScoreCard?,
        postedTs: Timestamp?,
        updatedTs: Timestamp?
    ) {
        this.postId = postId
        this.user = user
        this.message = message
        this.type = type
        this.scoreCard = scoreCard
        this.postedTs = postedTs
        this.updatedTs = updatedTs
    }

}