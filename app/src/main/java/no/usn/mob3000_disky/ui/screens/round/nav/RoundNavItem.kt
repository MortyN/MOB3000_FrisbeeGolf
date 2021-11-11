package no.usn.mob3000_disky.ui.screens.round.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

const val CURRENTROUND_ROUTE = "currentround"

sealed class RoundNavItem(var route: String, var icon: ImageVector, var title: String) {
    object AddRound : RoundNavItem("addround", Icons.Outlined.Article, "Ny Runde")
    object ChooseTrack : RoundNavItem("choosetrack", Icons.Outlined.Analytics, "Velg Bane")
    object ChoosePlayers : RoundNavItem("chooseplayers", Icons.Outlined.ControlPoint, "Velg Spillere")
    object CurrentRound : RoundNavItem("currentround", Icons.Outlined.AccountCircle, "Nåværende Runde")

}