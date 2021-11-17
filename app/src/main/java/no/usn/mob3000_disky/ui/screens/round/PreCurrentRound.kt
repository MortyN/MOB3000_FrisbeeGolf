package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.DismissDirection.*
import androidx.compose.material.DismissValue.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.components.searchbar.UserObjectSearchBox
import no.usn.mob3000_disky.ui.theme.SelectedBlue
import kotlin.math.round

@ExperimentalMaterialApi
@Composable
fun SwipeableUserList(
    items: List<ScoreCardMember>,
    dismissed: (listItem: ScoreCardMember) -> Unit
) {
    LazyColumn {
        items(items,
            {listItem: ScoreCardMember -> listItem.user.userId}
        ) { item ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(EndToStart)){
                dismissed(item)
            }
            SwipeToDismiss(
                state = dismissState,
                modifier = Modifier
//                    .padding(vertical = 4.dp),
                ,
                directions = setOf(EndToStart),
                background = {
                    val scale by animateFloatAsState(
                        if (dismissState.targetValue == Default) 0.75f else 1f
                    )
                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(horizontal = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = "delete icon",
                            modifier = Modifier.scale(scale)
                        )
                    }
                },
                dismissContent = {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RectangleShape,
                        elevation = animateDpAsState(
                            if (dismissState.dismissDirection != null) 4.dp else 0.dp
                        ).value
                    ) {
                        Divider(color = Color.LightGray,modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = rememberImagePainter(
                                    APIUtils.s3LinkParser(item.user.imgKey),
                                    builder = {
                                        crossfade(true)
                                        placeholder(R.drawable.ic_profile)
                                        scale(coil.size.Scale.FILL)
                                        transformations(CircleCropTransformation())
                                        error(R.drawable.ic_profile)
                                    }
                                ),
                                contentDescription = "",
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(60.dp)
                            )
                            Text(text = "${item.user.firstName} ${item.user.lastName}")
                        }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreCurrentRoundPreview() {

}

@Composable
fun DividerCurrentRound() {
    Spacer(modifier = Modifier.height(4.dp))
    Divider(color = Color.Gray,modifier = Modifier
        .fillMaxWidth()
        .width(2.dp))
    Spacer(modifier = Modifier.height(4.dp))
}

@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun PreCurrentRound(
    navHostController: NavHostController,
    track: ArenaRound,
    selectedArena: Arena,
    loggedInUser: User,
    userViewModel: UserViewModel,
    roundViewModel: RoundViewModel
) {

    var playersRe = remember { mutableStateListOf<ScoreCardMember>() }
    val users = userViewModel.arenaList.value
    val loading = userViewModel.loading.value

    val scorecard = roundViewModel.scoreCard.value
    val scorecardmembers = roundViewModel.selectedScoreCardMembers

    LaunchedEffect(track){
        roundViewModel.setCurrentArenaRound(track)
        userViewModel.getUserList(UserFilter(null))
        roundViewModel.scoreCard.value.arenaRound = track
    }

    val addPlayerClicked = remember{ mutableStateOf(false) }


    if(addPlayerClicked.value){
        AlertDialog(
            onDismissRequest = {
                addPlayerClicked.value = false
            },
            title = {
                Text(text = "Velg Spiller")
            },
            text = {
                Column {
                    if(!loading){
                        UserObjectSearchBox(users, onItemSelected = { user ->
                            roundViewModel.addSelectedScoreCardMember(ScoreCardMember(user = user))
                            addPlayerClicked.value = false

                        })
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
                        Text("Avbryt")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(2f)
                .height(500.dp)
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
            Text(text = selectedArena.arenaName,
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
            Text(text = track.description,
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
                Row{
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
                    IconButton(onClick = {
                        roundViewModel.selectedScoreCardMembers.clear()

                    }) {
                        Box(modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(Color.Red), contentAlignment = Alignment.Center){
                            Icon(imageVector = Icons.Filled.Delete, "", tint = Color.White)
                        }
                    }
                }
            }

                SwipeableUserList(scorecardmembers, dismissed = { userItem ->
                        roundViewModel.removeSelectedScoreCardMember(userItem)
                })

        }
    }
}

