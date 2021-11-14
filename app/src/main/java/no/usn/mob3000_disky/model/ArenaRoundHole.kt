package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class ArenaRoundHole(
    @SerializedName("arenaRoundHoleId") var arenaRoundHoleId : Long?,
    @SerializedName("arenaRound") var arenaRound : ArenaRound,
    @SerializedName("holeName") var holeName : String,
    @SerializedName("parValue") var parValue : Int,
    @SerializedName("active") var active : Boolean,
    @SerializedName("start_latitude") var startLatitude : String,
    @SerializedName("start_longitude") var startLongitude : String,
    @SerializedName("end_latitude") var endLatitude : String,
    @SerializedName("end_longitude") var endLongitude : String,
    @SerializedName("order") var order : Int
)