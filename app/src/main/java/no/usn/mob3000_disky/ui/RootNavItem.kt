package no.usn.mob3000_disky.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import no.usn.mob3000_disky.ui.screens.round.nav.CURRENTROUND_ROUTE

//https://fonts.google.com/icons?icon.query=add

const val ROOT_ROUTE = "rootroute"

sealed class RootNavItem(var route: String, var icon: ImageVector, var title: String) {
    object Feed : RootNavItem("feed", Icons.Outlined.Article, "Feed")
    object MyRounds : RootNavItem("myrounds", Icons.Outlined.Analytics, "Mine Runder")
    object AddRound : RootNavItem("addround", Icons.Outlined.ControlPoint, "Ny Runde")
    object MyProfile : RootNavItem("myprofile", Icons.Outlined.AccountCircle, "Min Profil")
    object Profile : RootNavItem("profile", Icons.Outlined.AccountCircle, "Profil")
    object Friends : RootNavItem("friends", Icons.Outlined.People, "Venner")
    object AddArena : RootNavItem("addarena", Icons.Outlined.PlusOne, "Opprett Arena")
    object TrackRecords : RootNavItem("trackrecords", Icons.Outlined.Leaderboard, "Bane Rekorder")
    object MyTracks : RootNavItem("mytracks", Icons.Outlined.SettingsInputComponent, "Dine Baner")
    object Settings : RootNavItem("settings", Icons.Outlined.Settings, "Innstillinger")
    object ScoreCardSummary : RootNavItem("scorecardsummary", Icons.Outlined.Settings, "Spillekort")
    object ScoreCardPost : RootNavItem("scorecardpost", Icons.Outlined.Settings, "Opprett innlegg")
    object EditArena : RootNavItem("editarena", Icons.Outlined.Settings, "Endre/opprett arena")
}
