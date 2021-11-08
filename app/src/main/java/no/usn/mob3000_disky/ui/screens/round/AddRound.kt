package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.usn.mob3000_disky.model.ArenaFilter
import no.usn.mob3000_disky.model.PostFilter
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
fun AddRound(mainViewModel: RoundViewModel) {

    val resultsArenaList = mainViewModel.arenaList.value
    val results = mainViewModel.arenaStrList.value
    val loading = mainViewModel.loading.value

    if (resultsArenaList.isEmpty() && !loading) {
        mainViewModel.getArena(ArenaFilter(null))
    }

    val names = mainViewModel.arenaStrList.value

if (!loading && results.isNotEmpty()){
    Column(modifier = Modifier.fillMaxSize()){
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 0.dp, 0.dp, 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ArenaSearchBox(items = results)
        }
        GoogleMap(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight())

    }
}


}
