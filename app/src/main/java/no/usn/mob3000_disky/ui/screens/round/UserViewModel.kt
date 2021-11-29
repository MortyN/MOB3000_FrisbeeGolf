package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.round.ArenaRepository
import no.usn.mob3000_disky.repository.users.UserRepository
import no.usn.mob3000_disky.ui.screens.login.AuthViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repository: UserRepository,
): ViewModel() {
    val loggedInUser = mutableStateOf(User(0))

    var userList = mutableStateListOf<User>()
        private set

    fun removeUserFromList(user: User){
        userList.remove(user)
    }

    fun addUserToList(user: User){
        userList.add(user)
    }

    val loading = mutableStateOf(false)

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun getUserList(userFilter: UserFilter) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true

            val result = repository.getUserList(userFilter, loggedInUser.value.apiKey)
            userList.addAll(result)

            loading.value = false
        }
    }
}