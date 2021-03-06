package no.usn.mob3000_disky.ui.components.searchbar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.ui.components.searchbox.SearchBox
import no.usn.mob3000_disky.ui.screens.round.nav.RoundNavItem

@ExperimentalAnimationApi
@Composable
fun ArenaSearchBox(arenas: List<Arena>, navController: NavHostController) {
    SearchBox(
        items = arenas,
        itemContent = { arena ->
            ArenaSearchBoxItem(arena)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { arena ->
            value = arena.arenaName.toString()
            filter(value)
            val arenaJson = Gson().toJson(arena)
            view.clearFocus()
            navController.navigate(RoundNavItem.ChooseTrack.route.plus("/$arenaJson"))
        }

        TextSearchBar(
            modifier = Modifier,
            value = value,
            label = "Søk etter arena",
            onDoneActionClick = {
                view.clearFocus()
            },
            onClearClick = {
                value = ""
                filter(value)
                view.clearFocus()
            },
            onFocusChanged = { focusState ->
                isSearching = focusState.isFocused
            },
            onValueChanged = { query ->
                value = query
                filter(value)
            }
        )
    }
}

@Composable
fun ArenaSearchBoxItem(arena: Arena) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        arena.arenaName?.let { Text(text = it, style = MaterialTheme.typography.subtitle2) }
    }
}
