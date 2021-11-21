package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList

data class ArenaRound(
    @SerializedName("arenaRoundId") var arenaRoundId : Long = 0,
    @SerializedName("arena") var arena : Arena = Arena(),
    @SerializedName("holeAmount") var holeAmount : Int = 0,
    @SerializedName("payment") var payment : Boolean = false,
    @SerializedName("description") var description : String = "",
    @SerializedName("createdBy") var createdBy : User = User(0),
    @SerializedName("creationTs") var creationTs : String = "",
    @SerializedName("updateTs") var updateTs : String = "",
    @SerializedName("holes") var arenaRoundHoles : List<ArenaRoundHole> = ArrayList()
)