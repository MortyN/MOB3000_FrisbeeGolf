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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import no.usn.mob3000_disky.ui.components.autocomplete.AutoCompleteBox
import no.usn.mob3000_disky.ui.components.autocomplete.AutoCompleteSearchBarTag
import no.usn.mob3000_disky.ui.components.autocomplete.asAutoCompleteEntities
import java.util.*

@ExperimentalAnimationApi
@Composable
fun ArenaSearchBox(items: List<String>, navController: NavHostController) {

    val autoCompleteEntities = items.asAutoCompleteEntities(
        filter = { item, query ->
            item.lowercase(Locale.getDefault())
                .startsWith(query.lowercase(Locale.getDefault()))
        }
    )

    AutoCompleteBox(
        items = autoCompleteEntities,
        itemContent = { item ->
            ValueAutoCompleteItem(item.value)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { item ->
            value = item.value
            filter(value)
            navController.navigate("")
            view.clearFocus()
        }

        TextSearchBar(
            modifier = Modifier.testTag(AutoCompleteSearchBarTag),
            value = value,
            label = "Search by value",
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
fun ValueAutoCompleteItem(item: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = item, style = MaterialTheme.typography.subtitle2)
    }
}