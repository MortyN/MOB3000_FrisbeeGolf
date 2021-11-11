package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import no.usn.mob3000_disky.ui.components.autocomplete.AutoCompleteEntity
import java.sql.Timestamp
import java.util.*

class Arena (
    var arenaId: Long? = null,
    var arenaName: String? = null,
    var description: String? = null,
    var established: Date? = null,
    var createdBy: User? = null,
    var createdTs: Timestamp? = null,
    var updateTs: Timestamp? = null,
    var latitude: String? = null,
    var longitude: String? = null,
    var rounds: List<ArenaRound>? = null,
    var active: Boolean = false
        ):AutoCompleteEntity {

    constructor (arenaId: Long) : this(arenaId, null, null, null, null, null, null, null, null) {
        this.arenaId = arenaId
    }

    override fun filter(query: String): Boolean {
        return arenaName?.lowercase(Locale.getDefault())
            ?.startsWith(query.toLowerCase(Locale.getDefault()))!!
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