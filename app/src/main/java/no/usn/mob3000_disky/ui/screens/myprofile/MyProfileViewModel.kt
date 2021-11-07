package no.usn.mob3000_disky.ui.screens.myprofile

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.Interaction
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import javax.inject.Inject

//https://dagger.dev/hilt/view-model.html

//https://www.youtube.com/watch?v=QDcBO4iROyE&t=106s

//https://www.youtube.com/watch?v=gaa8KcyJqCU

//https://github.com/ahmedeltaher/MVVM-Kotlin-Android-Architecture/blob/master/app/src/main/java/com/task/ui/component/recipes/RecipesListViewModel.kt

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val repository: PostRepository
): ViewModel(){

    val postList: MutableState<List<Post>> = mutableStateOf(ArrayList())

    val createPostResult: MutableState<Post> = mutableStateOf(Post(
        null,
        User(0),
        "",
        0,
        null,
        "",
        "",
        ArrayList<Interaction>()
    ))

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getRepo() = repository

    fun getPosts(user: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = repository.getFeed(user)
            postList.value = result
        }
    }


    fun createPost(post: Post){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = repository.createPost(post)
            createPostResult.value = result
        }
    }

}
