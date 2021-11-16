package no.usn.mob3000_disky.ui.screens.round.nav

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.gson.Gson
import kotlinx.coroutines.delay
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.*


@ExperimentalAnimationApi
fun NavGraphBuilder.addRoundNavGraph(
    navController: NavHostController,
    roundViewModel: RoundViewModel,
    userViewModel: UserViewModel,
    loggedInUser: User
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
        composable(RoundNavItem.PreCurrentRound.route.plus("/{arena}/{track}"),
            arguments = listOf(
                navArgument("arena") { type = NavType.StringType },
                navArgument("track") { type = NavType.StringType }
            )
        ) { backStackEntry ->
                PreCurrentRound(
                    navHostController = navController,
                    track = Gson().fromJson(backStackEntry?.arguments?.getString("track"),
                        ArenaRound::class.java),
                    selectedArena = Gson().fromJson(backStackEntry?.arguments?.getString("arena"),
                        Arena::class.java), loggedInUser, userViewModel)


        }
    }

}