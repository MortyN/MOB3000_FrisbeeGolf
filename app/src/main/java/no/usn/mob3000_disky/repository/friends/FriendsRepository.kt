package no.usn.mob3000_disky.repository.friends

import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.PostFilter
import no.usn.mob3000_disky.model.UserLink
import no.usn.mob3000_disky.model.UserLinkFilter

interface FriendsRepository {
    suspend fun getFriends(filter: UserLinkFilter, idToken: String): List<UserLink>

    suspend fun updateFriend(userLink: UserLink, idToken: String): UserLink
}