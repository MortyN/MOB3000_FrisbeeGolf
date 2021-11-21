package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class ArenaRoundHole(
    @SerializedName("arenaRoundHoleId") var arenaRoundHoleId : Long = 0,
    @SerializedName("arenaRound") var arenaRound : ArenaRound = ArenaRound(),
    @SerializedName("holeName") var holeName : String = "",
    @SerializedName("parValue") var parValue : Int = 0,
    @SerializedName("active") var active : Boolean = true,
    @SerializedName("start_latitude") var startLatitude : Double = 0.0,
    @SerializedName("start_longitude") var startLongitude : Double = 0.0,
    @SerializedName("end_latitude") var endLatitude : Double = 0.0,
    @SerializedName("end_longitude") var endLongitude : Double = 0.0,
    @SerializedName("order") var order : Int = 0
)