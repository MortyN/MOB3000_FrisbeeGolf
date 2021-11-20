package no.usn.mob3000_disky.ui.screens.round

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import no.usn.mob3000_disky.R
import coil.transform.CircleCropTransformation
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.gson.Gson
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.api.APIUtils
import no.usn.mob3000_disky.model.*
import no.usn.mob3000_disky.ui.RootNavItem
import no.usn.mob3000_disky.ui.screens.round.nav.RoundNavItem
import no.usn.mob3000_disky.ui.theme.BtnAcceptGreen

@ExperimentalAnimationApi
@Composable
fun CurrentRound(roundViewModel: RoundViewModel, navController: NavHostController) {

    val scoreCard = roundViewModel.scoreCard.value
    
    val users = roundViewModel.selectedScoreCardMembers

    var currentRoundHole = roundViewModel.currentRoundHole.value

    val density = LocalDensity.current
    LaunchedEffect(roundViewModel.newScoreCard.value.cardId){
        if(roundViewModel.newScoreCard.value.cardId != 0L){
            val scoreCardJson = Gson().toJson(roundViewModel.newScoreCard.value.cardId)
            navController.navigate(RootNavItem.ScoreCardSummary.route.plus("/$scoreCardJson"))
        }
    }

    Scaffold(
        bottomBar = {CurrentRoundBottomBar(roundViewModel = roundViewModel)},
        floatingActionButton = {
            AnimatedVisibility(
                visible = currentRoundHole == scoreCard.arenaRound.arenaRoundHoles.last(),
                enter = fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 250)
                )
            ) {
                FloatingActionButton(onClick = {
                    roundViewModel.createScoreCard()
                }, modifier = Modifier.padding(10.dp), backgroundColor = BtnAcceptGreen, contentColor = Color.White){

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 12.dp)) {
                        Text(text = "Ferdig", modifier = Modifier.padding(horizontal = 5.dp))
                        Icon(imageVector = Icons.Default.Send, contentDescription = "")
                    }
                }

            }

        }

    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            horizontal = 30.dp,
                            vertical = 10.dp
                        )
                        .fillMaxWidth()) {
                    Text(text = currentRoundHole.holeName, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    ParCircle(parAmount = currentRoundHole.parValue)
                }
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .background(Color.Blue)) {
//
//                }
                ShowArenaHoleMap(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), roundViewModel.currentRoundHole.value)

                LazyColumn{
                    items(items = users, itemContent = { item ->
                        PlayerListItem(item, roundViewModel = roundViewModel)
                    })
                }
            }
        }
    }
}

@Composable
fun ShowArenaHoleMap(modifier: Modifier = Modifier, arenaRoundHole: ArenaRoundHole) {
    val mapView = rememberMapViewWithLifeCycle()

    Column(
        modifier = modifier
    ) {
        AndroidView(
            {mapView}
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.clear()
                map.uiSettings.isZoomControlsEnabled = true

                val holeStart =  LatLng(arenaRoundHole.startLatitude.toDouble(), arenaRoundHole.startLongitude.toDouble())
                val holeEnd = LatLng(arenaRoundHole.endLatitude.toDouble(), arenaRoundHole.endLongitude.toDouble())
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(holeStart,18f))
                val markerOptions = MarkerOptions()
                    .title("Start")
                    .position(holeStart).icon(
                        BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_GREEN))
                map.addMarker(markerOptions)

                val markerOptionsDestination = MarkerOptions()
                    .title("Slutt")
                    .position(holeEnd).icon(
                        BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_RED))
                map.addMarker(markerOptionsDestination)

                map.addPolyline(
                    PolylineOptions().add( holeStart, holeEnd))
            }
        }
    }
}


    @Composable
    fun CurrentRoundBottomBar(
        roundViewModel: RoundViewModel,
//        navController: NavHostController
    ) {
        val scoreCard = roundViewModel.scoreCard.value
        var selectedTabIndex by remember { mutableStateOf(0) }

        ScrollableTabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier
            .fillMaxWidth()
            .shadow(10.dp), backgroundColor = Color.White){
            scoreCard.arenaRound.arenaRoundHoles.forEach {
                Tab(
                    selected = selectedTabIndex == it.order-1,
                    onClick = {
                        selectedTabIndex = it.order-1

                        roundViewModel.nextRoundHole(roundHole = it)

                              },
                    modifier = Modifier.height(50.dp),
                    text = { Text(it.holeName) }
                )
            }
        }
    }


@Composable
fun PlayerListItem(member: ScoreCardMember, roundViewModel: RoundViewModel) {

//    val scoreCardResult = remember {
//        mutableStateOf(member.results.find { it.arenaRoundHole.arenaRoundHoleId == roundViewModel.currentRoundHole.value.arenaRoundHoleId })
//    }

    val scoreCard = roundViewModel.scoreCard.value

    val currentScoreCardResult = remember { mutableStateOf(member.results[roundViewModel.currentRoundHole.value.order-1])}
    
    val uiScoreCardCount = remember { mutableStateOf(currentScoreCardResult.value.scoreValue)}

    LaunchedEffect(key1 = roundViewModel.currentRoundHole.value.arenaRoundHoleId ){
        currentScoreCardResult.value = member.results[roundViewModel.currentRoundHole.value.order-1]
        uiScoreCardCount.value = currentScoreCardResult.value.scoreValue
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,  horizontalArrangement = Arrangement.SpaceBetween) {
            Image(
                painter = rememberImagePainter(
                    APIUtils.s3LinkParser(member.user.imgKey),
                    builder = {
                        scale(Scale.FILL)
                        transformations(CircleCropTransformation())
                        crossfade(true)
                        placeholder(R.drawable.ic_profile)
                        error(R.drawable.ic_profile)
                    }),
                contentDescription = R.drawable.ic_profile.toString(),
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Text(text = member.user.firstName, fontWeight = FontWeight.SemiBold)
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Button(onClick = {/*TODO*/}){
                    Text(text = "-")
                }
                Column(modifier = Modifier.width(50.dp), horizontalAlignment = Alignment.CenterHorizontally ) {
                    if(uiScoreCardCount.value == 0 ){
                        Text(text = "-")
                    }else{
                        Text(text = uiScoreCardCount.value.toString())
                    }
                }
                Button(onClick = {
                    if(uiScoreCardCount.value == 0 ){
                        currentScoreCardResult.value.scoreValue = roundViewModel.currentRoundHole.value.parValue
                        uiScoreCardCount.value = roundViewModel.currentRoundHole.value.parValue
                    }else{
                        currentScoreCardResult.value.scoreValue = currentScoreCardResult.value.scoreValue.plus(1)
                        uiScoreCardCount.value = uiScoreCardCount.value.plus(1)
                    }

                }) {
                    Text(text = "+")

                }
            }
        }
    }

}

@Composable
fun ParCircle(parAmount: Int) {
    Box(modifier = Modifier
        .size(50.dp)
        .clip(CircleShape)
        .background(Color(0xFF21C17C))){
        Column(modifier = Modifier.fillMaxSize(),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Par", fontSize = 12.sp, color = Color.White)
            Text(text = parAmount.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
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

