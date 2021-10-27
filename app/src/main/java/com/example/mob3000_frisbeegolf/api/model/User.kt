package com.example.mob3000_frisbeegolf.api.model

data class User(val userId: Long) {
//    constructor(
//        userId: Long,
//        userName: String,
//        firstName: String,
//        lastName: String,
//        phoneNumber: String,
//        password: String,
//        userLinks: MutableList<UserLink>
//    ) : this(userId)

}

data class UserResponse(val userId: Long,
                        val userName: String,
                        val firstName: String,
                        val lastName: String,
                        val phoneNumber: String,
                        val password: String,
                        val imgKey: String?,
                        val userLinks: MutableList<UserLink>?)