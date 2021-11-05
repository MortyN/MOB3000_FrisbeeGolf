package no.usn.mob3000_disky.model

import java.sql.Timestamp
import java.util.*

class User(
    var userId: Long,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val imgKey: String?,
    val userLinks: MutableList<UserLink>?){

    constructor (userId: Long) : this(userId, "", "", "", "", "", "", null) {
        this.userId = userId
    }


}

//class User {
//    private var cardId: Long
//    private var arenaRound: ArenaRound? = null
//    private var startTs: Date? = null
//    private var endTs: Date? = null
//    private var createdBy: User? = null
//
//    val userId: Long = null
//    val userName: String = null
//    val firstName: String = null
//    val lastName: String = null
//    val phoneNumber: String = null
//    val password: String = null
//    val imgKey: String? = null
//    val userLinks: MutableList<UserLink>?
//
//    constructor(cardId: Long) {
//        this.cardId = cardId
//    }
//
//    constructor(
//        cardId: Long,
//        arenaRound: ArenaRound?,
//        startTs: Date?,
//        endTs: Date?,
//        createdBy: User?
//    ) {
//        this.cardId = cardId
//        this.arenaRound = arenaRound
//        this.startTs = startTs
//        this.endTs = endTs
//        this.createdBy = createdBy
//    }
//}

class PostFilter(val user: User,
                 val type: Long?,
                 val scoreCardId: ScoreCard?,
                 val getFromConnections: Boolean)

class UserLink(
    private val userLink1: User,
    private val userLink2: User,
    private val status: Int,
    private val type: Int,
    private val createdTimeStamp: Timestamp
) {
    companion object {
        const val USER_LINK_STATUS_PENDING = 1
        const val USER_LINK_STATUS_ACCEPTED = 2
        const val USER_LINK_STATUS_DECLINED = 3
        const val USER_LINK_TYPE_FRIEND_CONNECTION = 1

        val columns: String
            get() = " user_links.USER_ID_LINK1, user_links.USER_ID_LINK2, user_links.STATUS, user_links.TYPE, user_links.CREATED_TS "
    }
}