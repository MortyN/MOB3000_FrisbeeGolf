package no.usn.mob3000_disky.ui.components.searchbox

typealias CustomFilter<T> = (T, String) -> Boolean

//adding custom function to List element
fun <T> List<T>.asSearchBoxAutoCompleteEntities(filter: CustomFilter<T>): List<ValueSearchBoxEntityInterface<T>> {
    return map {
        object : ValueSearchBoxEntityInterface<T> {
            override val value: T = it
            override fun filter(query: String): Boolean {
                return filter(value, query)
            }
        }
    }
}
