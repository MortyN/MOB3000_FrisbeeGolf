package no.usn.mob3000_disky.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.di.RetrofitModule
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.repository.auth.AuthValidationRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthValidationRepository,
): ViewModel() {

    val loggedInUser = mutableStateOf(User(0));
    val isLoggedIn = mutableStateOf(false)


    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun signIn(idToken: String?) {
        if (idToken != null) {
            viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
                val result = repository.validategso(idToken)
                loggedInUser.value = result
                if (loggedInUser.value!!.userId > 0) {
                    isLoggedIn.value = true;
                }
            }
        }
    }

    fun signInTestUser(userId: Long) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = repository.getTestUser(userId)
            loggedInUser.value = result
            if (loggedInUser.value!!.userId > 0) {
                isLoggedIn.value = true
            }
        }
    }
}