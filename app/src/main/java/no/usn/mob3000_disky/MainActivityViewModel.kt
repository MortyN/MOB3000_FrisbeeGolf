package no.usn.mob3000_disky

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.auth.AuthRepository
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    val loggedInUser: MutableState<User> = mutableStateOf(User(0))

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getLoggedInUser(userId: Int){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = repository.getOneUser(userId)
            loggedInUser.value = result
        }
    }
}
