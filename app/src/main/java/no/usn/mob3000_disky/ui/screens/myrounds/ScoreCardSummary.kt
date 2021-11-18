package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.google.gson.Gson
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.Utils
import no.usn.mob3000_disky.ui.Utils.Companion.getTimeAgo

@Composable
fun ScoreCardSummary(scoreCardId: Long, loggedInUser: User, mainViewModel: MyRoundViewModel, navController: NavHostController){

    LaunchedEffect(true){
        mainViewModel.getOneScoreCard(scoreCardId)
    }

    val scoreCard = remember { mutableStateOf(mainViewModel.scoreCard.value) }

if(scoreCard.value.cardId != 0L){
    Column(modifier = Modifier.padding(16.dp)) {
        Row(){
            Text("Vear diskgolf", fontWeight = FontWeight.Bold)
            Text("- Ukesgolf", fontWeight = FontWeight.Light)
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text("${Utils.getDate(scoreCard.value.startTs).getTimeAgo()} - 18 hull", fontWeight = FontWeight.Light)
        }

        Column(modifier = Modifier.padding(top = 10.dp)) {
            scoreCard.value.members.forEach {
                userResults(it)
            }
        }

        ScoreCardResultTable(scoreCard.value, 32.dp)

        Button(
            onClick = {
                val scoreCardJson = Gson().toJson(scoreCard)
                navController.navigate(RootNavItem.ScoreCardPost.route.plus("/$scoreCardJson"))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = "Localized description",
                Modifier.padding(end = 8.dp)
            )
            Text(text = "Del poengkort med venner")
        }

    }
}

}

@Composable
fun ScoreCardResultTable(scoreCard: ScoreCard, padding: Dp) {
    var length = scoreCard.arenaRound.arenaRoundHoles.size
    var firstHoles = scoreCard.arenaRound.arenaRoundHoles.take(9)
    var midHoles: List<ArenaRoundHole> = ArrayList()
    var lastHoles: List<ArenaRoundHole> = ArrayList()

    generateScoreTable(firstHoles, scoreCard.members, padding)

    if(firstHoles.size == 9){
        midHoles = scoreCard.arenaRound.arenaRoundHoles.subList(9, if(length > 18) 17 else length)
        generateScoreTable(midHoles, scoreCard.members, padding)
    }

    if(midHoles.size == 18){
        lastHoles = scoreCard.arenaRound.arenaRoundHoles.subList(18,if(length > 27) 27 else length)
        generateScoreTable(lastHoles, scoreCard.members, padding)
    }

}


@Composable
fun generateScoreTable(holes: List<ArenaRoundHole>, members: List<ScoreCardMember>, padding: Dp){
    Column(modifier = Modifier.padding(top = padding)) {
        Row() {
            Text("Hull", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Start)
            // Header Row
            holes.forEach { hole ->
                Text(hole.order.toString(), modifier = Modifier
                    .weight(0.1f),
                    textAlign = TextAlign.Center)
            }
        }
        Row() {
            Text("Par", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Start)
            // Header Row
            holes.forEach { hole ->
                Text(hole.parValue.toString(), modifier = Modifier
                    .weight(0.1f), fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        members.forEach { member ->
            Row(){
                Text(member.user.firstName,  modifier = Modifier.weight(0.2f))
                holes.forEach { hole ->
                    var scoreValue = getScoreValue(member, hole)
                    var scoreDiff: Int? = null

                    if(scoreValue != null){
                        scoreDiff =  scoreValue - hole.parValue
                    }

                    var color: Color = when(scoreDiff){
                        -2-> Color(0xFF078DF8)
                        -1 -> Color(0xFF4DB0FF)
                        0 -> Color(0xFFFFFFFF)
                        1 -> Color(0xFFFFC267)
                        2 -> Color(0xFFF7A52D)
                        else -> Color(0xFFFF6969)
                    }

                    Text(
                        scoreValue?.toString() ?: "-",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .weight(0.1f)
                            .background(color)
                            .border(0.3.dp, Color.White)
                    )
                }
            }

        }
    }
}

@Composable
fun userResults(member: ScoreCardMember){

    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 10.dp)){
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
                .weight(0.1f)
        )
        var prefix = ""
        if(member.totalScore > 0 ) prefix = "+"

        Text(member.user.firstName, modifier = Modifier.weight(0.7f))
        Text("${prefix} ${member.totalScore.toString()}", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
        Text("(${member.totalThrows})", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
    }

}

fun getScoreValue(member: ScoreCardMember, hole: ArenaRoundHole): Int?{
    member.results?.forEach { result ->
        if(result.arenaRoundHole.arenaRoundHoleId == hole.arenaRoundHoleId){
            return result.scoreValue
        }
    }
    return null
}