package no.usn.mob3000_disky.ui.components.searchbox

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private typealias ItemSelected<T> = (T) -> Unit
//Stable is used to communicate some guarantees to the compose compiler about how a certain type or function will behave.
//https://github.com/androidx/androidx/blob/androidx-main/compose/docs/compose-api-guidelines.md#stable-types
@Stable
interface SearchBoxScope<T : SearchBoxEntityInterface> : SearchBoxDesignScope {
    var isSearching: Boolean
    fun filter(query: String)
    fun onItemSelected(block: ItemSelected<T> = {})
}

@Stable
interface SearchBoxDesignScope {
    var shouldWrapContentHeight: Boolean
    var boxWidthPercentage: Float
    var boxShape: Shape
    var boxBorderStroke: BorderStroke
    var boxMaxHeight: Dp
}

class SearchBoxState<T : SearchBoxEntityInterface>(private val startItems: List<T>) : SearchBoxScope<T> {
    private var onItemSelectedBlock: ItemSelected<T>? = null

    fun selectItem(item: T) {
        onItemSelectedBlock?.invoke(item)
    }

    var filteredItems by mutableStateOf(startItems)
    override var isSearching by mutableStateOf(false)
    override var boxShape: Shape by mutableStateOf(RoundedCornerShape(8.dp))
    override var boxWidthPercentage by mutableStateOf(.9f)
    override var boxBorderStroke by mutableStateOf(BorderStroke(2.dp, Color.Black))
    override var boxMaxHeight: Dp by mutableStateOf(TextFieldDefaults.MinHeight * 3)
    override var shouldWrapContentHeight by mutableStateOf(false)

    override fun filter(query: String) {
        filteredItems = startItems.filter { entity ->
            entity.filter(query)
        }
    }

    override fun onItemSelected(block: ItemSelected<T>) {
        onItemSelectedBlock = block
    }
}
