package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import no.usn.mob3000_disky.ui.components.searchbox.SearchBoxEntityInterface
import java.util.*
import kotlin.collections.ArrayList

class Arena (
    var arenaId: Long = 0,
    var arenaName: String = "",
    var description: String = "",
    var established: String = "",
    var createdBy: User = User(0),
    var createdTs: String = "",
    var updateTs: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var rounds: List<ArenaRound> = ArrayList(),
    var active: Boolean = false
        ):SearchBoxEntityInterface {

    constructor (arenaId: Long) : this() {
        this.arenaId = arenaId
    }

    override fun filter(query: String): Boolean {
        return arenaName.lowercase(Locale.getDefault())
            .startsWith(query.lowercase(Locale.getDefault()))
    }

}

data class ArenaFilter(
    @SerializedName("arenaIds")var arenaIds: List<Long>?,
    @SerializedName("names")var names: List<String>?,
    @SerializedName("createdBy")var createdBy: List<Long>?,
    @SerializedName("getArenaRounds")var getArenaRounds: Boolean?,
    @SerializedName("isActive")var isActive: Boolean?

){
    constructor (arenaIds: List<Long>?) : this(listOf(0), null, null, null, null) {
        this.arenaIds = arenaIds
    }
}