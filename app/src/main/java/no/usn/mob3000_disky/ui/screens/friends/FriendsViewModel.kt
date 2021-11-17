package no.usn.mob3000_disky.ui.screens.friends

import android.os.IBinder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.friends.FriendsRepository
import no.usn.mob3000_disky.repository.users.UserRepository
import javax.inject.Inject

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendRepo: FriendsRepository,
    private val userRepo: UserRepository
): ViewModel() {
    var friendsList: MutableState<List<UserLink>> = mutableStateOf(ArrayList())
    val pendingFriendList: MutableState<List<UserLink>> = mutableStateOf(ArrayList())
    val userList: MutableState<List<User>> = mutableStateOf(ArrayList())
    val users: MutableState<List<User>> = mutableStateOf(ArrayList())



    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getLists(loggedInUser: User) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            var friendListFilter = UserLinkFilter(loggedInUser, UserLink.USER_LINK_TYPE_ACCEPTED);
            friendsList.value = friendRepo.getFriends(friendListFilter)

            var pendingListFilter = UserLinkFilter(loggedInUser, UserLink.USER_LINK_TYPE_PENDING);

            pendingFriendList.value = friendRepo.getFriends(pendingListFilter)
            pendingFriendList.value =
                pendingFriendList.value.filter { link -> link.userLink2.userId == loggedInUser.userId }

            var userFilter = UserFilter(null, true);

            users.value = userRepo.getUserList(userFilter)

            userList.value = users.value.filter { user -> user.haveConnection(loggedInUser) < 2 }

            userList.value = userList.value.filter { user ->
                if(user.userId == loggedInUser.userId) return@filter false
                if (user.haveConnection(loggedInUser) == UserLink.USER_LINK_TYPE_NO_CONNECTION) return@filter true
                else if (user.haveConnection(loggedInUser) == UserLink.USER_LINK_TYPE_PENDING) {
                    var userLink =
                        user.userLinks.find { link -> link.userLink1.userId == loggedInUser.userId }
                    if (userLink != null) return@filter true
                }
                return@filter false
            }
        }
    }

    fun deleteFriend(senderUser: User, recipientUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val link =  userRepo.toggleFriend(ToggleWrapper(senderUser = senderUser, recipientUser = recipientUser))
            if(link.type == UserLink.USER_LINK_TYPE_NO_CONNECTION) {
                friendsList.value = friendsList.value.filter { link -> link.userLink1.userId != recipientUser.userId
                                                                        && link.userLink2.userId != recipientUser.userId }
                recipientUser.userLinks = ArrayList()
                userList.value += recipientUser
            }
        }
    }

    fun addFriend(senderUser: User, recipientUser: User){

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            var link =  userRepo.toggleFriend(ToggleWrapper(senderUser = senderUser, recipientUser = recipientUser))
            if(link != null && link.type == UserLink.USER_LINK_TYPE_PENDING) {
                recipientUser.userLinks += link
            }
        }
    }

    fun deleteFriendRequest(senderUser: User, recipientUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            userRepo.toggleFriend(ToggleWrapper(senderUser = senderUser, recipientUser = recipientUser))
            recipientUser.userLinks = ArrayList()

        }
    }

    fun declineFriendRequest(senderUser: User, recipientUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            var link = userRepo.toggleFriend(
                ToggleWrapper(
                    senderUser = senderUser,
                    recipientUser = recipientUser
                )
            )
            if (link.type == UserLink.USER_LINK_TYPE_NO_CONNECTION) {
                pendingFriendList.value = pendingFriendList.value.filter { link ->
                    link.userLink1.userId != senderUser.userId
                            && link.userLink2.userId != senderUser.userId
                }
                senderUser.userLinks = ArrayList()
                userList.value += senderUser
            }
        }
    }

     fun acceptFriendRequest(userLink: UserLink){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            userLink.type = UserLink.USER_LINK_TYPE_ACCEPTED
            val link =  friendRepo.updateFriend(userLink)
            friendsList.value += userLink
            pendingFriendList.value = pendingFriendList.value.filter { link -> !(link.userLink1.userId == userLink.userLink1.userId
                                                                                    && link.userLink2.userId == userLink.userLink2.userId) }
        }
    }
}