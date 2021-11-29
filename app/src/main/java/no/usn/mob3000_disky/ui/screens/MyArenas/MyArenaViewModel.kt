package no.usn.mob3000_disky.ui.screens.MyArenas

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.repository.myprofile.PostRepository
import no.usn.mob3000_disky.repository.round.ArenaRepository
import no.usn.mob3000_disky.repository.score_card.ScoreCardRepository
import no.usn.mob3000_disky.ui.screens.login.AuthViewModel
import javax.inject.Inject

@HiltViewModel
class MyArenaViewModel @Inject constructor(
    private val arenaRepo: ArenaRepository,
): ViewModel() {

    val loggedInUser = mutableStateOf(User(0))

    val arenas: MutableState<List<Arena>> = mutableStateOf(ArrayList())

    var currentArenaHole = mutableStateOf(ArenaRoundHole())
       private set

    var currentArena = mutableStateOf(Arena())
        private set

    var arenaCreateResult = mutableStateOf(Arena())
       private set

    var currentDroppedMarkerLocation = mutableStateOf(LatLng(0.0,0.0))
        private set

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun saveArena(arena: Arena){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler){
            arenaCreateResult.value = arenaRepo.createArena(arena, loggedInUser.value.apiKey)
        }
    }

    fun getArena(loggedInUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            val result = arenaRepo.getArena(ArenaFilter(null,null,listOf(loggedInUser.userId), true, null), loggedInUser.apiKey)

            arenas.value = result

        }
    }
}
