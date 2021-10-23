package com.example.mob3000_frisbeegolf.model

import com.google.gson.annotations.SerializedName

data class Usertest (
    @SerializedName("userId") val userId : Int,
    @SerializedName("userName") val userName : String,
    @SerializedName("firstName") val firstName : String,
    @SerializedName("lastName") val lastName : String,
    @SerializedName("phoneNumber") val phoneNumber : Int,
    @SerializedName("password") val password : String,
    @SerializedName("userLinks") val userLinks : String
)