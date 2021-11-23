package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem

@Composable
    fun ScoreCardPost(scoreCard: ScoreCard, loggedInUser: User, mainViewModel: MyRoundViewModel, navController: NavHostController){
        val textState = remember { mutableStateOf(TextFieldValue()) }

        Column(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            ScoreCardResultTable(scoreCard, 5.dp)
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                modifier = Modifier.height(150.dp).padding(top = 30.dp).fillMaxWidth(),
                label = { Text("Hvordan var runden?") },
            )
            Button(
                onClick = {
                    mainViewModel.shareScoreCard(loggedInUser, scoreCard, textState.value.text)

                    navController.navigate(RootNavItem.Feed.route.plus("/true"))
                },
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Share,
                    contentDescription = "Localized description",
                    Modifier.padding(end = 8.dp)
                )
                Text(text = "Del nå")
            }
        }
    }