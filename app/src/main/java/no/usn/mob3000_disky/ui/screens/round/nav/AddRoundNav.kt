package no.usn.mob3000_disky.ui.screens.round.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.gson.Gson
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.AddRound
import no.usn.mob3000_disky.ui.screens.round.ChooseTrack
import no.usn.mob3000_disky.ui.screens.round.RoundViewModel
import kotlin.reflect.KClass


@ExperimentalAnimationApi
fun NavGraphBuilder.addRoundNavGraph(
    navController: NavHostController,
    roundViewModel: RoundViewModel
){
    navigation(
        startDestination = RootNavItem.AddRound.route,
        route = CURRENTROUND_ROUTE
    ){
        composable(AddRoundNavItem.AddRound.route) {
            AddRound(roundViewModel, navController = navController)
        }
        composable(AddRoundNavItem.ChooseTrack.route.plus("/{arena}"),
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
    }

}