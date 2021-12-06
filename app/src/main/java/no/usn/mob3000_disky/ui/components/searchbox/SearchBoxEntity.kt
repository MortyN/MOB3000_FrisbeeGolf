package no.usn.mob3000_disky.ui.components.searchbox

import androidx.compose.runtime.Stable

//Stable is used to communicate some guarantees to the compose compiler about how a certain type or function will behave.
@Stable
interface SearchBoxEntityInterface {
    fun filter(query: String): Boolean
}
//Stable is used to communicate some guarantees to the compose compiler about how a certain type or function will behave.
@Stable
interface ValueSearchBoxEntityInterface<T> : SearchBoxEntityInterface {
    val value: T
}
