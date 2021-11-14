package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import no.usn.mob3000_disky.repository.score_card.ScoreCardRepository
import javax.inject.Inject

@HiltViewModel
class MyRoundViewModel @Inject constructor(
    private val scoreCardRepository: ScoreCardRepository
): ViewModel() {

    val postList: MutableState<List<Post>> = mutableStateOf(ArrayList())
}