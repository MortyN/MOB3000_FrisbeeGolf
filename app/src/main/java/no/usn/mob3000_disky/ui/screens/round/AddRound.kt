package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.ui.components.searchbar.ArenaSearchBox


@ExperimentalAnimationApi
@Preview
@Composable
fun AddRoundPreview() {
    val names = listOf("hei","hedsadsai","hedgsdgi","hesdasdsa","heigrehreh","heifwefwwfw","sadsadhei","heiasdgs")
    Column(modifier = Modifier.fillMaxSize()){
        Row(modifier = Modifier
            .background(Color.Blue)
            .fillMaxWidth()
            .height(80.dp)) {
            Text(text = "hei")
        }
        Row(modifier = Modifier
            .background(Color.Yellow)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Text(text = "hei")
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun AddRound(mainViewModel: RoundViewModel, navController: NavHostController) {

    val resultsArenaList = mainViewModel.arenaList.value
    val results = mainViewModel.arenaStrList.value
    val loading = mainViewModel.loading.value

    LaunchedEffect(key1 = Unit){
        mainViewModel.scoreCard.value = ScoreCard(cardId = 0L)
        mainViewModel.selectedScoreCardMembers = mutableStateListOf()
    }

    if (resultsArenaList.isEmpty() && !loading) {
        mainViewModel.getArena(
            ArenaFilter(
                null,
                null,
                null,
                getArenaRounds = true,
                isActive = true
            )
        )
    }

    if (!loading && results.isNotEmpty()){
        Column(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                ArenaSearchBox(arenas = resultsArenaList, navController = navController)
//                ArenaSearchBox(items = results, navController = navController)
            }
            AddArenaMap(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
                arenas = resultsArenaList,
                navController = navController
                )

        }
    }


}

@Composable
fun ArenaAutoCompleteItem(arena: Arena) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        arena.arenaName?.let { Text(text = it, style = MaterialTheme.typography.subtitle2) }
    }
}
