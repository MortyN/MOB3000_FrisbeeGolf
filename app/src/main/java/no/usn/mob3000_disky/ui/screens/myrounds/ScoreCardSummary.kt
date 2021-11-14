package no.usn.mob3000_disky.ui.screens.myrounds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.R
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.ScoreCard
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.RootNavItem

@Composable
fun ScoreCardSummary(scoreCard: ScoreCard, loggedInUser: User, mainViewModel: MyRoundViewModel){
    Column(modifier = Modifier.padding(16.dp)) {
        Row(){
            Text("Vear diskgolf", fontWeight = FontWeight.Bold)
            Text("- Ukesgolf", fontWeight = FontWeight.Light)
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text("5 dager siden - 18 hull", fontWeight = FontWeight.Light)
        }

        Column(modifier = Modifier.padding(top = 10.dp)) {
            userResults()
            userResults()
            userResults()
            userResults()
        }

        ScoreCardResultTable(scoreCard)

    }
}


@Preview(showBackground = true)
@Composable
fun preview(){
    Column(modifier = Modifier.padding(16.dp)) {
        Row(){
            Text("Vear diskgolf", fontWeight = FontWeight.Bold)
            Text("- Ukesgolf", fontWeight = FontWeight.Light)
        }
        Column(
            Modifier
                .fillMaxWidth()
        ) {
            Text("5 dager siden - 18 hull", fontWeight = FontWeight.Light)
        }

        Column(modifier = Modifier.padding(top = 10.dp)) {
            userResults()
            userResults()
            userResults()
            userResults()
        }
        Column(modifier = Modifier.padding(top = 10.dp)) {
            //ScoreCardResultTable()
        }
    }
}
@Composable
fun ScoreCardResultTable(scoreCard: ScoreCard) {
    val holeAmount = scoreCard.arenaRound.holeAmount?.toInt()
    Column() {
        Row() {
            Text("Hull", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Start)
            // Header Row
            for(i in 1..9){
                Text(i.toString(), modifier = Modifier
                    .weight(0.1f),
                    textAlign = TextAlign.Center)
            }
        }

        scoreCard.members.forEach { member ->
            Row(){
                Text(member.user.firstName,  modifier = Modifier.weight(0.2f))
                member.results.forEachIndexed { index, result ->
                  if(index < 9){
                      var scoreDiff = result.scoreValue - result.arenaRoundHole.parValue
                      var color = Color(0xFFFF6969)
                      when(scoreDiff){
                          -2-> color = Color(0xFF078DF8)
                          -1 -> color = Color(0xFF4DB0FF)
                          0 -> color = Color(0xFFFFFFFF)
                          1 -> color = Color(0xFFFFC267)
                          2 -> color = Color(0xFFF7A52D)
                      }
                      Text(result.scoreValue.toString(),
                          textAlign = TextAlign.Center,
                          modifier = Modifier
                              .weight(0.1f)
                              .background(color)
                              .border(0.3.dp,Color.White)
                      )
                  }
                }
                if(member.results.size < 10){
                    for(i in member.results.size+1..9){
                        Text("-", textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(color = Color(0xFFFF6969))
                                .weight(0.1f)
                                .border(0.3.dp,Color.White)
                        )
                    }
                }
            }
        }
    }

    if (holeAmount != null && holeAmount > 9) {
            Column() {
                Row() {
                    Text("Hull", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Start)
                    // Header Row
                    for(i in 10..18){
                        Text(i.toString(), modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
                    }
                }
                scoreCard.members.forEach { member ->
                    Row() {
                        Text(member.user.firstName, modifier = Modifier.weight(0.2f))
                        member.results.forEachIndexed { index, result ->
                            if(index in 10..17) {
                                var scoreDiff = result.scoreValue - result.arenaRoundHole.parValue
                                var color = Color(0xFFFF6969)
                                when(scoreDiff){
                                    -2-> color = Color(0xFF078DF8)
                                    -1 -> color = Color(0xFF4DB0FF)
                                    0 -> color = Color(0xFFFFFFFF)
                                    1 -> color = Color(0xFFFFC267)
                                    2 -> color = Color(0xFFF7A52D)
                                }
                                Text(result.scoreValue.toString(),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .background(color)
                                        .border(0.3.dp,Color.White)
                                )
                            }
                            if(member.results.size in 10..18){
                                for(i in member.results.size+1..18){
                                    Text("-", textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .background(color = Color(0xFFFF6969))
                                            .weight(0.1f)
                                            .border(0.3.dp,Color.White)
                                    )
                                }
                            }
                        }
                    }
                }
            }
    }

    if(holeAmount != null && holeAmount > 18){
        Column() {
            Row() {
                Text("Hull", modifier = Modifier.weight(0.2f), textAlign = TextAlign.Start)
                // Header Row
                for(i in 19..27){
                    Text(i.toString() ?: " ", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
                }
            }
            scoreCard.members.forEach { member ->
                Row() {
                    Text(member.user.firstName, modifier = Modifier.weight(0.2f))
                    member.results.forEachIndexed { index, result ->
                        if(index in 19..26) {
                            var scoreDiff = result.scoreValue - result.arenaRoundHole.parValue
                            var color = Color(0xFFFF6969)
                            when(scoreDiff){
                                -2-> color = Color(0xFF078DF8)
                                -1 -> color = Color(0xFF4DB0FF)
                                0 -> color = Color(0xFFFFFFFF)
                                1 -> color = Color(0xFFFFC267)
                                2 -> color = Color(0xFFF7A52D)
                            }
                            Text(result.scoreValue.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .weight(0.1f)
                                    .background(color)
                                    .border(0.3.dp,Color.White)
                            )
                        }
                        if(member.results.size < 18){
                            for(i in member.results.size+1..9){
                                Text("-", textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .background(color = Color(0xFFFF6969))
                                        .weight(0.1f)
                                        .border(0.3.dp,Color.White)
                                )
                            }
                        }
                    }
                }
            }

        }
    }

}

@Composable
fun userResults(){

    Row(verticalAlignment = Alignment.CenterVertically){
        Image(
            painter =
            rememberImagePainter(
                APIUtils.s3LinkParser("member.user.imgKey"),
                builder = {
                    scale(Scale.FILL)
                    transformations(CircleCropTransformation())
                    crossfade(true)
                    placeholder(R.drawable.ic_profile)
                    error(R.drawable.ic_profile)
                }),
            contentDescription = "member.user.firstName",
            modifier = Modifier
                .size(25.dp)
                .weight(0.1f)
        )

        Text("Håkon", modifier = Modifier.weight(0.7f))
        Text("+4", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
        Text("59", modifier = Modifier.weight(0.1f), textAlign = TextAlign.Center)
    }

}