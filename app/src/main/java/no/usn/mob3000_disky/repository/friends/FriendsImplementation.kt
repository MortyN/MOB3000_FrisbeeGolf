package no.usn.mob3000_disky.repository.friends

import no.usn.mob3000_disky.endpoints.FriendsAPIService
import no.usn.mob3000_disky.endpoints.PostAPIService
import no.usn.mob3000_disky.model.PostFilter
import no.usn.mob3000_disky.model.UserLink
import no.usn.mob3000_disky.model.UserLinkFilter

class FriendsImplementation constructor(
    private val friendsAPI: FriendsAPIService
): FriendsRepository  {

    override suspend fun getFriends(filter: UserLinkFilter, idToken: String): List<UserLink> {
        return friendsAPI.getFriends(filter, idToken)
    }

    override suspend fun updateFriend(userLink: UserLink, idToken: String): UserLink {
        return friendsAPI.updateFriend(userLink, idToken)
    }
}