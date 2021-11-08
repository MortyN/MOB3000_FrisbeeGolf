package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.util.*

//class Arena {
//    private var arenaId: Long
//    private var arenaName: String? = null
//    private var description: String? = null
//    private var established: Int? = null
//    private var createdBy: User? = null
//    private var createdTs: Date? = null
//    private var updateTs: Date? = null
//    private var active = false
//
//    constructor(arenaId: Long) {
//        this.arenaId = arenaId
//    }
//
//    constructor(
//        arenaId: Long,
//        arenaName: String?,
//        description: String?,
//        established: Int?,
//        createdBy: User?,
//        createdTs: Date?,
//        updateTs: Date?,
//        active: Boolean
//    ) {
//        this.arenaId = arenaId
//        this.arenaName = arenaName
//        this.description = description
//        this.established = established
//        this.createdBy = createdBy
//        this.createdTs = createdTs
//        this.updateTs = updateTs
//        this.active = active
//    }
//}

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
        ){

    constructor (arenaId: Long) : this(arenaId, null, null, null, null, null, null, null, null) {
        this.arenaId = arenaId
    }

}

data class ArenaFilter(
    @SerializedName("arenaIds")var arenaIds: List<Long>?,
    @SerializedName("names")var names: List<String>?,
    @SerializedName("createdBy")var createdBy: List<Long>?
){
    constructor (arenaIds: List<Long>?) : this(listOf(0), null, null) {
        this.arenaIds = arenaIds
    }
}