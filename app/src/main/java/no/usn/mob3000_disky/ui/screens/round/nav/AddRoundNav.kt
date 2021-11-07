package no.usn.mob3000_disky.ui.screens.round.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.AddRound

@ExperimentalAnimationApi
fun NavGraphBuilder.addRoundNavGraph(
    navController: NavHostController
){
    navigation(
        startDestination = RootNavItem.AddRound.route,
        route = CURRENTROUND_ROUTE
    ){
        composable(AddRoundNavItem.AddRound.route) {
            AddRound()
        }
    }
}