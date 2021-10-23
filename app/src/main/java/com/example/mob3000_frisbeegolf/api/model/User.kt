package com.example.mob3000_frisbeegolf.api.model

import com.example.mob3000_frisbeegolf.api.util.Parse

class User {
    private var userId: Long
    private var userName: String? = null
    private var firstName: String? = null
    private var lastName: String? = null
    private var phoneNumber: String? = null
    private var password: String? = null
    private var userLinks: MutableList<UserLink>? = null

    constructor(userId: Long) {
        this.userId = userId
    }

    constructor(
        userId: Long,
        userName: String?,
        firstName: String?,
        lastName: String?,
        phoneNumber: String?,
        password: String?
    ) {
        this.userId = userId
        this.userName = userName
        this.firstName = firstName
        this.lastName = lastName
        this.phoneNumber = phoneNumber
        this.password = password
    }

    fun addUserLink(link: UserLink) {
        if (!Parse.nullOrEmpty(userLinks)) userLinks = ArrayList()
        userLinks!!.add(link)
    }

    companion object {
        val columns: String
            get() = "users.USER_ID, users.USERNAME, users.FIRST_NAME, " +
                    "users.LAST_NAME, users.PHONE_NUMBER, users.PASSWORD "
    }
}