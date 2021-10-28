package com.example.mob3000_frisbeegolf.activities.myprofile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mob3000_frisbeegolf.api.endpoints.APIFeedInterface
import com.example.mob3000_frisbeegolf.api.filter.PostFilterByUser
import com.example.mob3000_frisbeegolf.api.model.PostResponse
import com.example.mob3000_frisbeegolf.api.model.User
import com.example.mob3000_frisbeegolf.api.model.UserResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyProfileViewModel: ViewModel() {

    var feedListResponse:List<PostResponse> by mutableStateOf(listOf())
    var createPostResponse:PostResponse by mutableStateOf(PostResponse(
        null,
        UserResponse(
            0,
            "",
            "",
            "",
            "",
            "",
            null,
            null),
        "",
        0,
        null,
        "",
        ""
    ))
    var errorMessage: String by mutableStateOf("")

    val loading = mutableStateOf(false)

    fun getFeedList(): List<PostResponse> {
        viewModelScope.launch {
            loading.value = true


            val apiService = APIFeedInterface.getInstance()
            try {
                val res = apiService.getProfileFeed(PostFilterByUser(User(110), null, null, false))
                feedListResponse = res
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
            delay(2000)
            loading.value = false

        }

        return feedListResponse
    }

    fun createPost(post: PostResponse): PostResponse {
        viewModelScope.launch {
            loading.value = true

            val apiService = APIFeedInterface.getInstance()
            try {
                val res = apiService.createFeedPost(post)
                createPostResponse = res
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
            delay(2000)
            loading.value = false

        }

        return createPostResponse
    }



}