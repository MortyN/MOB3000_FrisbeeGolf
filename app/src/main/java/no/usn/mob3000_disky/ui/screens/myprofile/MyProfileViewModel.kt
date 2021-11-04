package no.usn.mob3000_disky.ui.screens.myprofile

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.liveLiteral
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

//https://github.com/ahmedeltaher/MVVM-Kotlin-Android-Architecture/blob/master/app/src/main/java/com/task/ui/component/recipes/RecipesListViewModel.kt

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

    //https://developer.android.com/reference/androidx/annotation/VisibleForTesting
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val postListLiveDataPrivate = MutableLiveData<List<Post>>()
    val postListLiveData: LiveData<List<Post>> get() = postListLiveDataPrivate

//    fun getPostListLive(user: User){
//        viewModelScope.launch {
//            repository.getProfileFeed(user = user).coll
//        }
//    }

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