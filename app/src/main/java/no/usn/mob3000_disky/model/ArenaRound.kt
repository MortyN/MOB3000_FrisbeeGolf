package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class ArenaRound(
    @SerializedName("arenaRoundId") var arenaRoundId : Long?,
    @SerializedName("arena") var arena : Arena,
    @SerializedName("holeAmount") var holeAmount : Int?,
    @SerializedName("payment") var payment : Boolean?,
    @SerializedName("description") var description : String?,
    @SerializedName("createdBy") var createdBy : User?,
    @SerializedName("creationTs") var creationTs : Date?,
    @SerializedName("updateTs") var updateTs : Date?,
    @SerializedName("holes") var arenaRoundHoles : List<ArenaRoundHole> = ArrayList()
)