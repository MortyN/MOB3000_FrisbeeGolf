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
import no.usn.mob3000_disky.model.Interaction
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

    fun interactPost(interaction: Interaction){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//           val interactionResult: Interaction = repository.interactPost(interaction)
//            if(interactionResult == null){
//                val newInteractionList = ArrayList<Interaction>()
//                val post = feedList.value.find { feedPost -> feedPost.postId == interaction.post.postId }
//                if (post != null) {
//                    post.interactions.filterTo(newInteractionList, { i -> i.post.postId != interaction.post.postId && i.user.userId != interaction.user.userId })
//                    post.interactions = newInteractionList
//                }
//            } else{
//                val post = feedList.value.find { feedPost -> feedPost.postId == interaction.post.postId }
//
//
//                if (post != null) {
//                    post.interactions = post.interactions.plus(interaction)
//                    print(post);
//                }
//            }
//        }
//
//       feedList.value[0].interactions += interaction

            val interactionResult: Interaction = repository.interactPost(interaction)


        }
    }


//    fun deletePost(postId: Long){
//        viewModelScope.launch {
//            val result = repository.deletePost(postId)
//            feedList.value = result
//        }
//    }

}