package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.google.android.libraries.maps.model.LatLng
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.round.ArenaRepository
import no.usn.mob3000_disky.repository.score_card.ScoreCardRepository
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.login.AuthViewModel
import javax.inject.Inject

@HiltViewModel
class RoundViewModel @Inject constructor(
    private val arenaRepository: ArenaRepository,
    private val scoreCardRepository: ScoreCardRepository,
): ViewModel() {

    val loggedInUser = mutableStateOf(User(0))

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    var isCardFinished = mutableStateOf(false)
        private set

    val arenaList: MutableState<List<Arena>> = mutableStateOf(ArrayList())
    val arenaStrList: MutableState<List<String>> = mutableStateOf(ArrayList())

    var currentArenaRound = mutableStateOf(ArenaRound())
        private set

    var selectedScoreCardMembers = mutableStateListOf<ScoreCardMember>()


    var scoreCard = mutableStateOf(ScoreCard(cardId = 0L))
        private set

    var newScoreCard = mutableStateOf(ScoreCard())
        private set

    var currentLocation = mutableStateOf(LatLng(0.0,0.0))
        private set

    var currentRoundHole = mutableStateOf(ArenaRoundHole())
        private set

    val loading = mutableStateOf(false)

    val createSCloading = mutableStateOf(false)

    var selectedIndexArenaHoleId = mutableStateOf(0)

    fun initScoreCardResults(){
        selectedScoreCardMembers.forEach { member ->
            scoreCard.value.arenaRound.arenaRoundHoles.forEach { hole ->
                member.results = member.results.plus(ScoreCardResult(scoreCardMember = ScoreCardMember(user = member.user), arenaRoundHole = hole, scoreValue = 0))
            }
        }
    }

    fun setCurrentArenaRound(track: ArenaRound){
        currentArenaRound.value = track
        scoreCard.value.arenaRound = currentArenaRound.value
    }


    fun addSelectedScoreCardMember(scoreCardMember: ScoreCardMember){
        selectedScoreCardMembers.add(scoreCardMember)
        scoreCard.value.members = selectedScoreCardMembers
    }

    fun removeSelectedScoreCardMember(scoreCardMember: ScoreCardMember){
        selectedScoreCardMembers.remove(scoreCardMember)
    }


    fun nextRoundHole(roundHole: ArenaRoundHole){
        currentRoundHole.value = roundHole
    }

    fun createScoreCard(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            createSCloading.value = true
            scoreCard.value.createdBy = loggedInUser.value;
            val result = scoreCardRepository.createScoreCard(scoreCard = scoreCard.value, loggedInUser.value.apiKey)

            newScoreCard.value = result

            createSCloading.value = false
            isCardFinished.value = true
        }
    }

    fun getArena(arenaFilter: ArenaFilter) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true
            val templist: MutableList<String> = arrayListOf()

            val result = arenaRepository.getArena(arenaFilter, loggedInUser.value.apiKey)

            for (res in result){
                res.arenaName.let { templist.add(it) }
            }

            arenaStrList.value = templist
            arenaList.value = result
            loading.value = false
        }
    }
}
