package no.usn.mob3000_disky.repository.friends

import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.PostFilter
import no.usn.mob3000_disky.model.UserLink
import no.usn.mob3000_disky.model.UserLinkFilter

interface FriendsRepository {
    suspend fun getFriends(filter: UserLinkFilter): List<UserLink>

    suspend fun updateFriend(userLink: UserLink): UserLink
}