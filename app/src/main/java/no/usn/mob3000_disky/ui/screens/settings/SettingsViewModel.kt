package no.usn.mob3000_disky.ui.screens.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
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
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: UserRepository
): ViewModel() {
    var imgFile = mutableStateOf(File(""))
    val loggedInUser = mutableStateOf(User(0L))
    val firstName = mutableStateOf(TextFieldValue(loggedInUser.value.firstName))
    val lastName = mutableStateOf(TextFieldValue(loggedInUser.value.lastName))

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        throwable.printStackTrace()
    }

    suspend fun updateUser(file: File) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            lateinit var user: User
            val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)

            if (firstName.value.text.isNotEmpty()) {
                loggedInUser.value.firstName = firstName.value.text
            }

            if (lastName.value.text.isNotEmpty()) {
                loggedInUser.value.lastName = lastName.value.text
            }
            user = if (file.isFile) {
                repository.updateUser(loggedInUser.value, MultipartBody.Part.createFormData("image", file.getName(), requestFile), loggedInUser.value.apiKey)
            } else {
                repository.updateUserNoImage(loggedInUser.value, loggedInUser.value.apiKey)
            }
            loggedInUser.value.imgKey = user.imgKey
            loggedInUser.value.firstName = user.firstName
            loggedInUser.value.lastName = user.lastName
        }
    }

}

