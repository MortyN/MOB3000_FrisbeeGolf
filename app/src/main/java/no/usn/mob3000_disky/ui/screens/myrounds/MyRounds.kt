package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.ScoreCardMember
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem


@Composable
fun MyRounds(mainViewModel: MyRoundViewModel, loggedInUser: User, navController: NavHostController) {
    val rounds = mainViewModel.rounds.value

    LaunchedEffect(key1 = Unit){
        mainViewModel.getScoreCard(loggedInUser)
    }

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(rounds) { scoreCard ->
            RoundItem(scoreCard, navController)
        }
    }
}

@Composable
fun RoundItem(scoreCard: ScoreCard, navController: NavHostController){
    scoreCard.members?.toMutableList()?.sortBy {it.totalPar}
    var firstMembers = scoreCard.members?.take(4)

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(16.dp, 16.dp)
            .clickable(
                onClick = {
                    val scoreCardJson = Gson().toJson(scoreCard.cardId)
                    navController.navigate(RootNavItem.ScoreCardSummary.route.plus("/$scoreCardJson"))
                }
            )
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(){
                Text("${scoreCard.arenaRound?.arena?.arenaName}", fontWeight = FontWeight.Bold)
                Text("- ${scoreCard.arenaRound?.description}", fontWeight = FontWeight.Light)
            }
            Column(
                Modifier
                    .fillMaxWidth()
            ) {
                Text("5 dager siden - ${scoreCard.arenaRound?.holeAmount} hull", fontWeight = FontWeight.Light)
            }

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Row(modifier = Modifier
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween

                ){
                    firstMembers?.forEach { m ->
                        PersonScore(m)
                    }
                }
            }

        }

    }
}

@Composable
fun PersonScore(member: ScoreCardMember){
    Column() {
        Row(){
            Image(
                painter =
                rememberImagePainter(
                    APIUtils.s3LinkParser(member.user.imgKey),
                    builder = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                        crossfade(true)
                        placeholder(R.drawable.ic_profile)
                        error(R.drawable.ic_profile)
                    }),
                contentDescription = member.user.firstName,
                modifier = Modifier
                    .size(25.dp)
            )
            Column(modifier = Modifier.padding(start = 2.dp)) {
                Text(member.user.firstName, fontWeight = FontWeight.Bold, fontSize = 10.sp)
                Text(
                    if(member.totalPar >= 0 ) "+"
                    else{
                        "-"
                    } +
                    "${member.totalPar} (${member.totalThrows})",

                    fontSize = 10.sp)
            }
        }
    }
}


