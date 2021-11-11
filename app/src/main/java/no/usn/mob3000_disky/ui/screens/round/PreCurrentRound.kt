package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.model.Post
import no.usn.mob3000_disky.ui.screens.feed.PostFeedListItem
import no.usn.mob3000_disky.ui.screens.round.nav.RoundNavItem

@Preview(showBackground = true)
@Composable
fun PreCurrentRoundPreview() {



    val players = listOf("Hans Berglien", "Petter Stordalen", "Thomas Dahl")

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Din runde",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp )
        }
        DividerCurrentRound()
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(text = "Arena",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp))
            Text(text = "Arena",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
        }
        DividerCurrentRound()
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .background(Color.Blue)) {
            Text("map")
        }
        DividerCurrentRound()
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(text = "Bane",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp))
            Text(text = "Arena",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
        }
        DividerCurrentRound()
        Column() {
            Row(modifier = Modifier.padding(vertical = 10.dp)) {
                Text(text = "Spillere",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 20.dp))
                IconButton(onClick = {

                }) {
                    Icon(Icons.Filled.PlusOne, "")
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(players) { p ->
                    PlayerListItemPreview(
                        player = p,
                        0,
                        0,
                    )
                }

            }
        }

    }
}

@Composable
fun PlayerListItemPreview(player: String, index: Int, selectedIndex: Int,) {
    Row() {
        Text(player)
    }
}

@Composable
fun DividerCurrentRound() {
    Spacer(modifier = Modifier.height(4.dp))
    Divider(color = Color.Gray,modifier = Modifier
        .fillMaxWidth()
        .width(2.dp))
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun PreCurrentRound(navHostController: NavHostController, track: ArenaRound) {
    Column(){
        ShowArenaMap(modifier = Modifier.fillMaxWidth(), navController = navHostController, arena = track.arena)
    }
}