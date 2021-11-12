package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.model.UserFilter
import no.usn.mob3000_disky.ui.components.searchbar.UserObjectSearchBox
import no.usn.mob3000_disky.ui.theme.SelectedBlue

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
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Spillere",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
                IconButton(onClick = {

                }) {
                    Box(modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(SelectedBlue), contentAlignment = Alignment.Center){
                        Icon(imageVector = Icons.Filled.PlusOne, "", tint = Color.White)
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(players) { p ->
                    PlayerListItem(
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
fun PlayerListItem(player: String, index: Int, selectedIndex: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                APIUtils.s3LinkParser("2otpp4sqlc6p4v9c0udesi1sbk"),
                builder = {
                    scale(coil.size.Scale.FILL)
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.logo)
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(60.dp)
                .padding(horizontal = 10.dp)
        )
        Text(text = player)
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

@ExperimentalAnimationApi
@Composable
fun PreCurrentRound(navHostController: NavHostController, track: ArenaRound, selectedArena: Arena, loggedInUser: User, userViewModel: UserViewModel) {

    val players = listOf(loggedInUser.firstName+ " " + loggedInUser.lastName, "Petter Stordalen", "Thomas Dahl")
    val users = userViewModel.arenaList.value
    val loading = userViewModel.loading.value

    LaunchedEffect(key1 = Unit){
        userViewModel.getUserList(UserFilter(null))
    }

    val addPlayerClicked = remember{ mutableStateOf(false) }

    if(addPlayerClicked.value){
        AlertDialog(
            onDismissRequest = {
                addPlayerClicked.value = false
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Column() {
//                    TextField(
//                        value = text,
//                        onValueChange = { text = it }
//                    )
                    Text("Custom Text")

                    if(!loading){
                        UserObjectSearchBox(users)
                    }
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { addPlayerClicked.value = false }
                    ) {
                        Text("Dismiss")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(2f).height(500.dp)
        )
    }

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
            Text(text = selectedArena.arenaName.orEmpty(),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
        }
        DividerCurrentRound()
        ShowArenaMap(modifier = Modifier
            .fillMaxWidth()
            .height(170.dp), navController = navHostController, arena = selectedArena)
        DividerCurrentRound()
        Row(modifier = Modifier.padding(vertical = 10.dp)) {
            Text(text = "Bane",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp))
            Text(text = track.description.orEmpty(),
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,)
        }
        DividerCurrentRound()
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Spillere",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp)
                IconButton(onClick = {

                    addPlayerClicked.value = true
                }) {
                    Box(modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(SelectedBlue), contentAlignment = Alignment.Center){
                        Icon(imageVector = Icons.Filled.Add, "", tint = Color.White)
                    }
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(players) { p ->
                    PlayerListItem(
                        player = p,
                        0,
                        0,
                    )
                }

            }
        }

    }
}