package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import no.usn.mob3000_disky.model.ArenaRound

@Preview
@Composable
fun ChoosePlayersPreview() {

}

@Composable
fun ChoosePlayers(navController: NavHostController,track: ArenaRound) {
    Text("${track.description} ${track.arena?.arenaName}")
}