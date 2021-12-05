package no.usn.mob3000_disky.ui.screens.MyArenas

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeCompilerApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.gson.Gson
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem

@Composable
fun MyArenas(loggedInUser: User, mainViewModel: MyArenaViewModel, navController: NavHostController) {
    val arenas = mainViewModel.arenas.value

    LaunchedEffect(key1 = Unit){
        mainViewModel.getArena(loggedInUser)
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mainViewModel.currentArena.value = Arena(createdBy = loggedInUser)
                    navController.navigate(RootNavItem.EditArena.route)
                },
                backgroundColor = Color.Blue,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }

    ) {
        Text("Dine Arenaer",
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth().padding(top = 20.dp)

        )
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp, 70.dp)
        ) {
            items(items = arenas) { a ->
                ArenaListItem(a, navController,mainViewModel)
            }
        }
    }
}

@Composable
fun ArenaListItem(arena: Arena, navController: NavHostController, mainViewModel: MyArenaViewModel){
    Card(elevation = 4.dp,
        modifier = Modifier
            .padding(top = 10.dp)
            .clickable {
                mainViewModel.currentArena.value = arena
                navController.navigate(RootNavItem.EditArena.route)
            }
        ) {

        Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(10.dp, 20.dp)) {
            Text(arena.arenaName)
            if(arena.rounds == null){
                Text("ingen runder", fontSize = 12.sp)
            } else if (arena.rounds.size < 2) {
                Text("${arena.rounds.size} runde", fontSize = 12.sp)
            } else {
                Text("${arena.rounds.size} runder", fontSize = 12.sp)
            }

        }
    }
}