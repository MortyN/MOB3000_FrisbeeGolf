package com.example.mob3000_frisbeegolf.api.model


class ScoreCardMember {
    private var scoreCardMemberId: Long
    private var user: User? = null
    private var scoreCard: ScoreCard? = null

    constructor(scoreCardMemberId: Long) {
        this.scoreCardMemberId = scoreCardMemberId
    }

    constructor(scoreCardMemberId: Long, user: User?, scoreCard: ScoreCard?) {
        this.scoreCardMemberId = scoreCardMemberId
        this.user = user
        this.scoreCard = scoreCard
    }
}