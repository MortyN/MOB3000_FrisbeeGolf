package no.usn.mob3000_disky.model

import com.google.gson.annotations.SerializedName

data class ArenaRoundHole(
    @SerializedName("arenaRoundHoleId") var arenaRoundHoleId : Long = 0,
    @SerializedName("arenaRound") var arenaRound : ArenaRound = ArenaRound(),
    @SerializedName("holeName") var holeName : String = "",
    @SerializedName("parValue") var parValue : Int = 0,
    @SerializedName("active") var active : Boolean = false,
    @SerializedName("start_latitude") var startLatitude : String = "",
    @SerializedName("start_longitude") var startLongitude : String = "",
    @SerializedName("end_latitude") var endLatitude : String = "",
    @SerializedName("end_longitude") var endLongitude : String = "",
    @SerializedName("order") var order : Int = 0
)