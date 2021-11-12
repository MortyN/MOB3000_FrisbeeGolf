package no.usn.mob3000_disky.ui.screens.round


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.ui.screens.round.nav.RoundNavItem

@Preview(showBackground = true)
@Composable
fun ChooseTrackPreview() {

    val itemsIndexedList = listOf("Vear expert", "Veldig fin banexd", "tøffeste i gata")

    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxWidth()
        .background(Color.White)) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Velg Bane", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            items(itemsIndexedList) { p ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Divider(color = Color.Gray,modifier = Modifier
                        .fillMaxWidth()
                        .width(2.dp))
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = p,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                            )
                        Text(
                            text = "18 hull",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider(color = Color.Gray,modifier = Modifier
            .fillMaxWidth()
            .width(2.dp))
        Spacer(modifier = Modifier.height(4.dp))
    }

}

@Composable
fun ChooseTrack(navController: NavHostController, arena: Arena) {

    Column {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            , horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Velg Bane", fontSize = 40.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn{
            items(arena.rounds.orEmpty()) { track ->
                TrackListItem(arena = arena,track = track, navController = navController)
            }
        }
    }


}

@Composable
fun TrackListItem(arena: Arena,track: ArenaRound, navController: NavHostController){
    Column(modifier = Modifier.clickable {
        val trackJson = Gson().toJson(track)
        val arenaJson = Gson().toJson(arena)
        navController.navigate(RoundNavItem.PreCurrentRound.route.plus("/$arenaJson/$trackJson"))
    }) {
        Spacer(modifier = Modifier.height(4.dp))
        Divider(color = Color.Gray,modifier = Modifier
            .fillMaxWidth()
            .width(2.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = track.description.orEmpty(),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
            )
            Text(
                text = "${track.holeAmount.toString()} hull",
                fontSize = 22.sp,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray,
            )
        }
    }
}