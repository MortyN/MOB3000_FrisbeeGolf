package no.usn.mob3000_disky.ui.screens.myrounds

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
import no.usn.mob3000_disky.repository.score_card.ScoreCardRepository
import org.w3c.dom.Text
import javax.inject.Inject

@HiltViewModel
class MyRoundViewModel @Inject constructor(
    private val scoreCardRepo: ScoreCardRepository,
    private val postRepository: PostRepository
): ViewModel() {

    val rounds: MutableState<List<ScoreCard>> = mutableStateOf(ArrayList())

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getScoreCard(loggedInUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            rounds.value =  scoreCardRepo.getScoreCard(ScoreCardFilter(loggedInUser, 0))
        }
    }

    fun shareScoreCard(loggedInUser: User, scoreCard: ScoreCard, text: String){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            postRepository.createPost(Post(message = text, scoreCard = scoreCard, user = loggedInUser, type = 2))
        }
    }
}