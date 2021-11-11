package no.usn.mob3000_disky.ui.screens.round.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.gson.Gson
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.*


@ExperimentalAnimationApi
fun NavGraphBuilder.addRoundNavGraph(
    navController: NavHostController,
    roundViewModel: RoundViewModel
){
    navigation(
        startDestination = RootNavItem.AddRound.route,
        route = CURRENTROUND_ROUTE
    ){
        composable(RoundNavItem.AddRound.route) {
            AddRound(roundViewModel, navController = navController)
        }
        composable(RoundNavItem.ChooseTrack.route.plus("/{arena}"),
            arguments = listOf(
                navArgument("arena") { type = NavType.StringType }
            )
            ) {
            backStackEntry ->
            backStackEntry?.arguments?.getString("arena")?.let { json ->
                val arena = Gson().fromJson(json, Arena::class.java)
                ChooseTrack(navController = navController, arena = arena)
            }
        }
        composable(RoundNavItem.PreCurrentRound.route.plus("/{track}"),
            arguments = listOf(
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            backStackEntry?.arguments?.getString("track")?.let { json ->
                val track = Gson().fromJson(json, ArenaRound::class.java)
                PreCurrentRound(navHostController = navController, track = track)
            }
        }
    }

}