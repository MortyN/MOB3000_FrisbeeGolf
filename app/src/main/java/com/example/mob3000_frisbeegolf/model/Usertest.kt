package com.example.mob3000_frisbeegolf.model

import com.google.gson.annotations.SerializedName
import com.example.mob3000_frisbeegolf.api.model.UserResponse


data class Posttest (

    @SerializedName("postId") var postId : Int,
    @SerializedName("user") var user : UserResponse,
    @SerializedName("message") var message : String,
    @SerializedName("type") var type : Int,
    @SerializedName("scoreCard") var scoreCard : String,
    @SerializedName("postedTs") var postedTs : String,
    @SerializedName("updatedTs") var updatedTs : String

)

//class Posttest2 {
//    private var postId: Long
//    private var user: User
//    private var message: String? = null
//    private var type = 0
//    private var scoreCard: ScoreCard? = null
//    private var postedTs: Timestamp? = null
//    private var updatedTs: Timestamp? = null
//
////    constructor(postId: Long) {
////        this.postId = postId
////    }
////
////    constructor(
////        postId: Long,
////        user: User?,
////        message: String?,
////        type: Int,
////        scoreCard: ScoreCard?,
////        postedTs: Timestamp?,
////        updatedTs: Timestamp?
////    ) {
////        this.postId = postId
////        this.user = user
////        this.message = message
////        this.type = type
////        this.scoreCard = scoreCard
////        this.postedTs = postedTs
////        this.updatedTs = updatedTs
////    }
//}


data class Usertest (
    @SerializedName("userId") val userId : Int,
    @SerializedName("userName") val userName : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("phoneNumber") val phoneNumber : Int,
    @SerializedName("password") val password : String,
    @SerializedName("userLinks") val userLinks : String
)