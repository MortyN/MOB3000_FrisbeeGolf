package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import no.usn.mob3000_disky.R
import coil.transform.CircleCropTransformation
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*

@Preview(showBackground = true)
@Composable
fun CurrentRoundPreview() {

    var scoreCard = ScoreCard()

    scoreCard.members = listOf(
        ScoreCardMember(user = User(0, firstName = "Petter", lastName = "Stordalen")),
        ScoreCardMember(user = User(0, firstName = "Hans", lastName = "Lillelien"))
        )

    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    horizontal = 30.dp,
                    vertical = 20.dp
                )
                .fillMaxWidth()) {
            Text(text = "Hull 01", fontWeight = FontWeight.Bold, fontSize = 30.sp)
            ParCircle(parAmount = 5)
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.Blue)) {

        }

        LazyColumn{
            items(items = scoreCard.members, itemContent = { item ->
                PlayerListItem(item)
            })
        }
    }
}

@Composable
fun PlayerListItem(member: ScoreCardMember) {

    Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = rememberImagePainter(
                    APIUtils.s3LinkParser(""),
                    builder = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                        crossfade(true)
                        placeholder(R.drawable.ic_profile)
                        error(R.drawable.ic_profile)
                    }),
                contentDescription = R.drawable.ic_profile.toString(),
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
            )
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Button(onClick = {/*TODO*/}){
                    Text(text = "-")
                }
                Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
                    Text(text = "14")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "+")
                }
            }
        }
        Text(text = member.user.firstName + " " + member.user.lastName)
    }

}

@Composable
fun ParCircle(parAmount: Int) {
    Box(modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)
        .background(Color(0xFF21C17C))){
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Par", fontSize = 12.sp, color = Color.White)
            Text(text = parAmount.toString(), fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ParPreview() {
    Box(modifier = Modifier
        .size(70.dp)
        .clip(CircleShape)
        .background(Color(0xFF21C17C))){
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Par", fontSize = 12.sp, color = Color.White)
            Text(text = "4", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun CurrentRound(roundViewModel: RoundViewModel, navController: NavController) {
    Text(text = roundViewModel.selectedScoreCardMembers[0].user.firstName)

}