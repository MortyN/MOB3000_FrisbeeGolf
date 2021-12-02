package no.usn.mob3000_disky.ui.screens.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.MainActivityViewModel
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.repository.users.UserRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    val loggedInUser = mutableStateOf(User(0L))

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        throwable.printStackTrace()
    }

    suspend fun updateUser(file: File) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            val user = repository.updateUser(loggedInUser.value, MultipartBody.Part.createFormData("image", file.getName(), requestFile), loggedInUser.value.apiKey)
            loggedInUser.value.imgKey = user.imgKey
        }
    }

}

