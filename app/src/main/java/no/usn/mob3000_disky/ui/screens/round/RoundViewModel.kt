package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue

import androidx.compose.runtime.livedata.observeAsState
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.round.ArenaRepository
import javax.inject.Inject

@HiltViewModel
class RoundViewModel @Inject constructor(
    private val repository: ArenaRepository
): ViewModel() {

    val arenaList: MutableState<List<Arena>> = mutableStateOf(ArrayList())
    val arenaStrList: MutableState<List<String>> = mutableStateOf(ArrayList())

    val selectedUsers: MutableState<List<User>> = mutableStateOf(ArrayList())

    var currentArenaRound = mutableStateOf(ArenaRound())
        private set

    var selectedScoreCardMembers = mutableStateListOf<ScoreCardMember>()
        private set

    val scoreCard: MutableState<ScoreCard> = mutableStateOf(ScoreCard())


    val loading = mutableStateOf(false)


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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun getArena(arenaFilter: ArenaFilter) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            loading.value = true
            val templist: MutableList<String> = arrayListOf()

            val result = repository.getArena(arenaFilter)

            for (res in result){
                res.arenaName.let { templist.add(it) }
            }

            arenaStrList.value = templist
            arenaList.value = result
            loading.value = false
        }
    }
}
