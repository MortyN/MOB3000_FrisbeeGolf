package no.usn.mob3000_disky.ui.screens.feed

import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCompositionContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: PostRepository): ViewModel() {

    val feedList: MutableState<List<Post>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getPosts(user: User){
        user.getFromConnections = true
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true
            val result = repository.getFeed(user)
            feedList.value = result
            loading.value = false
        }
    }

//    fun deletePost(postId: Long){
//        viewModelScope.launch {
//            val result = repository.deletePost(postId)
//            feedList.value = result
//        }
//    }

}