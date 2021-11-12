package no.usn.mob3000_disky.ui.screens.feed

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
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import javax.inject.Inject

//https://dagger.dev/hilt/view-model.html

//https://www.youtube.com/watch?v=QDcBO4iROyE&t=106s

//https://www.youtube.com/watch?v=gaa8KcyJqCU

//https://github.com/ahmedeltaher/MVVM-Kotlin-Android-Architecture/blob/master/app/src/main/java/com/task/ui/component/recipes/RecipesListViewModel.kt

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val postRepository: PostRepository
    //private val userRepository: UserRepository
): ViewModel(){

    val postList: MutableState<List<Post>> = mutableStateOf(ArrayList())
    val postFilter: MutableState<PostFilter> = mutableStateOf(PostFilter(User(0), false))

    val loading = mutableStateOf(false)
    val createPostResult: MutableState<Post> = mutableStateOf(Post(
        null,
        User(0),
        "",
        0,
        null,
        "",
        "",
        Interactions()
    ))

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getRepo() = postRepository

    fun getPosts(filter: PostFilter){
        postFilter.value = filter
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true
            val result = postRepository.getFeed(filter)
            postList.value = result
            delay(500) //Leave me alone, no questions.
            loading.value = false
        }
    }
    fun createPost(post: Post){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = postRepository.createPost(post)
            createPostResult.value = result

            postList.value += result;
        }
    }

    fun interactPost(interaction: Interaction){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            postRepository.interactPost(interaction)
        }
    }

    fun isFriends(profileUser: User, loggedInUser: User): Int {
        return profileUser.haveConnection(loggedInUser)
    }

    fun onFriendIconClicked(loggedInUser: User, profileUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val link = null
            //userRepository.toggleFriend(loggedInUser, profileUser)
            if(link != null) profileUser.userLinks
            else{
                //Deleted
                profileUser.userLinks = profileUser.userLinks.filter { link -> link.userLink1.userId == profileUser.userId || link.userLink2.userId == profileUser.userId }
            }
        }
    }

    fun deletePost(postId: Int){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            postRepository.deletePost(postId)
            postList.value = postList.value.filter { post -> post.postId != postId }
        }

    }
}
