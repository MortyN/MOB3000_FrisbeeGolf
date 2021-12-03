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
    val name = mutableStateOf(TextFieldValue(""))
    var imgFile = mutableStateOf(File(""))
    val loggedInUser = mutableStateOf(User(0L))

    private val exceptionHandler = CoroutineExceptionHandler {_, throwable ->
        throwable.printStackTrace()
    }

    suspend fun updateUser(file: File) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
            if (name.value.text.isNotEmpty()) {
                loggedInUser.value.firstName = name.value.text
            }
            val user = repository.updateUser(loggedInUser.value, MultipartBody.Part.createFormData("image", file.getName(), requestFile), loggedInUser.value.apiKey)
            loggedInUser.value.imgKey = user.imgKey
        }
    }

}

