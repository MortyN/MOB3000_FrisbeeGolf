package com.example.mob3000_frisbeegolf.api.model

import java.sql.Timestamp

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