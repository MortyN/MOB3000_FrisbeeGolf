package no.usn.mob3000_disky.model;

import com.google.gson.annotations.SerializedName

data class ToggleWrapper(
    @SerializedName("senderUser") var senderUser : User,
    @SerializedName("recipientUser") var recipientUser : User
)
