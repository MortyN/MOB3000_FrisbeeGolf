package no.usn.mob3000_disky.ui.screens.myprofile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.endpoints.MyProfileAPIService
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.repository.myprofile.MyProfileRepository
import javax.inject.Inject

//https://dagger.dev/hilt/view-model.html

//https://www.youtube.com/watch?v=QDcBO4iROyE&t=106s

//https://www.youtube.com/watch?v=gaa8KcyJqCU


//    override suspend fun getProfileFeed(user: PostFilterByUser): List<Post> {
//        viewModelScope.launch {
//            loading.value = true
//
//            val apiService = MyProfileAPIService.getInstance()
//            try {
//                val res = apiService.getProfileFeed(PostFilterByUser(User(110), null, null, false))
//                feedListResponse = res
//            }
//            catch (e: Exception) {
//                errorMessage = e.message.toString()
//            }
//            delay(2000)
//            loading.value = false
//
//        }
//
//        return feedListResponse
//    }
//
//    override suspend fun createFeedPost(post: Post): Post {
//        viewModelScope.launch {
//            loading.value = true
//
//            val apiService = MyProfileAPIService.getInstance()
//            try {
//                val res = apiService.createFeedPost(post)
//                createPostResponse = res
//            }
//            catch (e: Exception) {
//                errorMessage = e.message.toString()
//            }
//            delay(2000)
//            loading.value = false
//
//        }
//
//        return createPostResponse
//    }


@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val repository: MyProfileRepository
): ViewModel(){

    val postList: MutableState<List<Post>> = mutableStateOf(ArrayList())
    val createPostResult: MutableState<Post> = mutableStateOf(Post(
        null,
        User(0),
        "",
        0,
        null,
        "",
        ""
    ))

    init {
        println("VIEWMODEL: $repository")

    }

    fun getRepo() = repository

    fun getPosts(user: User){
        viewModelScope.launch {
            val result = repository.getProfileFeed(user)
            postList.value = result
        }
    }

    fun createPost(post: Post){
        viewModelScope.launch {
            val result = repository.createFeedPost(post)
            createPostResult.value = result
        }
    }

}

//class MyProfileViewModel: ViewModel() {
//
//    var feedListResponse:List<Post> by mutableStateOf(listOf())
//    var createPostResponse:Post by mutableStateOf(Post(
//        null,
//        User(0),
//        "",
//        0,
//        null,
//        "",
//        ""
//    ))
//    var errorMessage: String by mutableStateOf("")
//
//    val loading = mutableStateOf(false)
//
//
//
//
//
//}