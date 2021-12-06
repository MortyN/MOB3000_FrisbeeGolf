package no.usn.mob3000_disky.ui.components.searchbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@ExperimentalAnimationApi
@Composable
fun <T : SearchBoxEntityInterface> SearchBox(
    items: List<T>,
    itemContent: @Composable (T) -> Unit,
    content: @Composable SearchBoxScope<T>.() -> Unit
) {
    val searchBoxState = remember { SearchBoxState(startItems = items) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        searchBoxState.content()
        AnimatedVisibility(visible = searchBoxState.isSearching) {
            LazyColumn(
                //applying base modifier
                modifier = Modifier.autoComplete(searchBoxState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(searchBoxState.filteredItems) { item ->
                    Box(modifier = Modifier.clickable { searchBoxState.selectItem(item) }) {
                        itemContent(item)
                    }
                }
            }
        }
    }
}

//base modifier for all future references
private fun Modifier.autoComplete(
    searchBoxItemScope: SearchBoxDesignScope
): Modifier = composed {
    val baseModifier = if (searchBoxItemScope.shouldWrapContentHeight)
        wrapContentHeight()
    else
        heightIn(0.dp, searchBoxItemScope.boxMaxHeight)

    baseModifier
        .fillMaxWidth(searchBoxItemScope.boxWidthPercentage)
        .border(
            border = searchBoxItemScope.boxBorderStroke,
            shape = searchBoxItemScope.boxShape
        )
}
