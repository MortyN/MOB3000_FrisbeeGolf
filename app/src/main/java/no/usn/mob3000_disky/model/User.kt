package no.usn.mob3000_disky.model

import java.util.*

class User(
    var userId: Long,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val password: String,
    val imgKey: String?,
    var userLinks: List<UserLink> = ArrayList<UserLink>()
    ){

    constructor (userId: Long) : this(userId, "", "", "", "", "", "",  ArrayList<UserLink>()) {
        this.userId = userId
    }

    fun haveConnection(user: User): Int{

        return this.userLinks.find { link -> link.userLink1.userId == user.userId || link.userLink2.userId == user.userId }?.type ?: 0
    }
}

class UserLink(
    val userLink1: User,
    val userLink2: User,
    val status: Int,
    val type: Int,
) {
    companion object {
        const val USER_LINK_TYPE_PENDING = 1
        const val USER_LINK_TYPE_ACCEPTED = 2

        val columns: String
            get() = " user_links.USER_ID_LINK1, user_links.USER_ID_LINK2, user_links.STATUS, user_links.TYPE, user_links.CREATED_TS "
    }



}