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
import javax.inject.Inject

@HiltViewModel
class MyArenaViewModel @Inject constructor(
    private val arenaRepo: ArenaRepository
): ViewModel() {

    val arenas: MutableState<List<Arena>> = mutableStateOf(ArrayList())

    var currentDroppedMarkerLocation = mutableStateOf(LatLng(0.0,0.0))
        private set

    private val exceptionHandler = CoroutineExceptionHandler{ _, throwable->
        throwable.printStackTrace()
    }

    fun getArena(loggedInUser: User){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            arenas.value =  arenaRepo.getArena(ArenaFilter(null,null,listOf(loggedInUser.userId), true, null))
        }
    }
}
