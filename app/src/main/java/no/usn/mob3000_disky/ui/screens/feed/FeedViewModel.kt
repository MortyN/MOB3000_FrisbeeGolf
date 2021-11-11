package no.usn.mob3000_disky.ui.screens.feed

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(private val repository: PostRepository): ViewModel() {

    val feedList: MutableState<List<Post>> = mutableStateOf(ArrayList())

    val loading = mutableStateOf(false)

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getPosts(user: PostFilter){
        user.getFromConnections = false

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true
            val result = repository.getFeed(user)
            feedList.value = result
            loading.value = false
        }
    }

    fun interactPost(interaction: Interaction){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {















        }








    }


//    fun deletePost(postId: Long){
//        viewModelScope.launch {
//            val result = repository.deletePost(postId)
//            feedList.value = result
//        }
//    }

}