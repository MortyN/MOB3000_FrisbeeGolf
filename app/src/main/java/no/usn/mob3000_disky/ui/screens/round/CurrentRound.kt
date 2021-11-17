package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import no.usn.mob3000_disky.model.ScoreCard

@Preview
@Composable
fun CurrentRoundPreview() {

}

@Composable
fun CurrentRound(roundViewModel: RoundViewModel, navController: NavController) {
    roundViewModel.scoreCard.value?.members?.get(0)?.user?.let { Text(text = it.firstName) }

}