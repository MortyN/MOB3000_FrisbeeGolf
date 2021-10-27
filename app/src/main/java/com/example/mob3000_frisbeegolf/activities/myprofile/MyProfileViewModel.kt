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
import kotlinx.coroutines.launch

class MyProfileViewModel: ViewModel() {
    var feedListResponse:List<PostResponse> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    fun getFeedList(): List<PostResponse> {
        viewModelScope.launch {
            val apiService = APIFeedInterface.getInstance()
            try {
                val movieList = apiService.getProfileFeed(PostFilterByUser(User(110), null, null, false))
                feedListResponse = movieList
            }
            catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
        return feedListResponse
    }



}