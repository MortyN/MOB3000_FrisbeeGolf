package no.usn.mob3000_disky.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

//https://fonts.google.com/icons?icon.query=add

sealed class NavItem(var route: String, var icon: ImageVector, var title: String) {
    object Feed : NavItem("feed", Icons.Outlined.Article, "Feed")
    object MyRounds : NavItem("myrounds", Icons.Outlined.Analytics, "Mine Runder")
    object AddRound : NavItem("addround", Icons.Outlined.ControlPoint, "Ny Runde")
    object MyProfile : NavItem("myprofile", Icons.Outlined.AccountCircle, "Min Profil")
    object Friends : NavItem("friends", Icons.Outlined.People, "Venner")
    object AddArena : NavItem("addarena", Icons.Outlined.PlusOne, "Opprett Arena")
    object TrackRecords : NavItem("trackrecords", Icons.Outlined.Leaderboard, "Bane Rekorder")
    object MyTracks : NavItem("mytracks", Icons.Outlined.SettingsInputComponent, "Dine Baner")
    object Settings : NavItem("settings", Icons.Outlined.Settings, "Innstillinger")
}
