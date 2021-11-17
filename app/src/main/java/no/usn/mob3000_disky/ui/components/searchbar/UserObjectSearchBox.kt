package no.usn.mob3000_disky.ui.components.searchbar

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.components.autocomplete.AutoCompleteBox
import no.usn.mob3000_disky.ui.components.autocomplete.AutoCompleteSearchBarTag
import no.usn.mob3000_disky.ui.screens.round.RoundViewModel


@ExperimentalAnimationApi
@Composable
fun UserObjectSearchBox(users: List<User>, onItemSelected: (User) -> Unit) {

    AutoCompleteBox(
        items = users,
        itemContent = { user ->
            UserAutoCompleteItem(user)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { user ->
            value = user.firstName
            filter(value)
            view.clearFocus()
            onItemSelected(user)
        }

        TextSearchBar(
            modifier = Modifier.testTag(AutoCompleteSearchBarTag),
            value = value,
            label = "Søk etter bruker",
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
fun UserAutoCompleteItem(user: User) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row() {
            Text(text = "${user.firstName} ${user.lastName}", style = MaterialTheme.typography.subtitle2)
        }
    }
}
