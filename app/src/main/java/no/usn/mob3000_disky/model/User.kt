package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import no.usn.mob3000_disky.ui.components.searchbox.SearchBoxEntityInterface
import java.util.*

class User(
    var userId: Long,
    val userName: String = "",
    var firstName: String = "",
    var lastName: String = "",
    val phoneNumber: String = "",
    val password: String = "",
    val apiKey: String = "",
    var imgKey: String = "",
    var userLinks: List<UserLink> = ArrayList<UserLink>()
    ): SearchBoxEntityInterface{

    constructor (userId: Long) : this(userId, "", "", "", "", "", "", "",  ArrayList<UserLink>()) {
        this.userId = userId
    }

    override fun filter(query: String): Boolean {
        return firstName.lowercase(Locale.getDefault())
            .startsWith(query.lowercase(Locale.getDefault()))
    }

    fun haveConnection(user: User): Int{

        return this.userLinks.find { link -> link.userLink1.userId == user.userId || link.userLink2.userId == user.userId }?.type ?: 0
    }
}

class UserFilter(
    @SerializedName("userId")
    var userId: Long?,
    @SerializedName("getUserLinks")
    var getUserLinks: Boolean = false
)


class UserLink(
    val userLink1: User,
    val userLink2: User,
    val status: Int,
    var type: Int,
) {
    companion object {
        const val USER_LINK_TYPE_NO_CONNECTION = 0
        const val USER_LINK_TYPE_PENDING = 1
        const val USER_LINK_TYPE_ACCEPTED = 2
    }
}
class UserLinkFilter(
    val user: User,
    var type: Int,
)
