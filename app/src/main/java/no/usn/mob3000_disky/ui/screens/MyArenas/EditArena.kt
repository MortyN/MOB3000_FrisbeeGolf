package no.usn.mob3000_disky.ui.screens.MyArenas

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.Marker
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.model.ArenaRound
import no.usn.mob3000_disky.model.ArenaRoundHole
import no.usn.mob3000_disky.model.User
import no.usn.mob3000_disky.ui.screens.round.rememberMapViewWithLifeCycle
import no.usn.mob3000_disky.ui.theme.SelectedBlue

@Composable
fun EditArena(
    loggedInUser: User,
    arena: Arena?,
    arenaViewModel: MyArenaViewModel,
    navController: NavController
) {
    var currentArena = remember { mutableStateOf(arena) }
    val name = remember { mutableStateOf(TextFieldValue()) }
    val description = remember { mutableStateOf(TextFieldValue()) }
    val arenaRounds = remember { currentArena.value?.let { mutableStateOf(it.rounds) } }

    if (currentArena.value == null) {
        currentArena.value = Arena();

    } else {
        name.value = currentArena.value?.let { TextFieldValue(it.arenaName) }!!
        description.value = currentArena.value?.let { TextFieldValue(it.description) }!!
    }
    Column {
        if (arena != null) {
            ArenaMap(
                arena, modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp), arenaViewModel = arenaViewModel
            )
        }
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        ) {
            item {
                Column() {
                    TextField(
                        value = name.value,
                        onValueChange = {
                            name.value = it
                            currentArena.value!!.arenaName = it.text
                        },
                        modifier = Modifier
                            .padding(top = 30.dp)
                            .fillMaxWidth(),
                        label = { Text("Navn på arena") },
                    )

                    TextField(
                        value = description.value,
                        onValueChange = {
                            description.value = it
                            currentArena.value!!.description = it.text
                        },
                        modifier = Modifier
                            .height(150.dp)
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        label = { Text("Beskrivelse") },
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Runder",
                            fontSize = 20.sp
                        )
                        IconButton(onClick = {
                            if (arenaRounds != null) {
                                var tempList = ArrayList(arenaRounds.value)
                                tempList += ArenaRound()
                                arenaRounds.value = tempList
                                currentArena.value!!.rounds = tempList
                            }
                        }) {
                            Box(
                                modifier = Modifier
                                    .size(30.dp)
                                    .clip(CircleShape)
                                    .background(SelectedBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Filled.Add, "", tint = Color.White)
                            }
                        }
                    }
                }
            }
            if (arenaRounds != null) {
                items(items = arenaRounds.value) { a ->
                    ArenaRoundItem(a)
                }
            }
        }
    }

}


@Composable
fun ArenaRoundItem(arenaRound: ArenaRound) {
    val arenaRoundHoles = remember { mutableStateOf(arenaRound.arenaRoundHoles) }

    var editMode by remember {
        mutableStateOf(false)
    }

    val name = remember { mutableStateOf(TextFieldValue()) }
    if (arenaRound.arenaRoundId == 0L) {
        name.value = TextFieldValue(arenaRound.description)
    } else {
        name.value = TextFieldValue(arenaRound.description)
    }

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 5.dp)
    ) {

        if (!editMode) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(arenaRound.description)
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { editMode = true },
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Localized description"
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(name.value.text)
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = { editMode = false },
                    ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Localized description"
                        )
                    }
                }

                TextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        arenaRound.description = it.text
                    },
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth(),
                    label = { Text("Navn på arena") },
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Hull",
                        fontSize = 20.sp
                    )
                    IconButton(onClick = {
                        var tempList = ArrayList(arenaRoundHoles.value)
                        tempList += ArenaRoundHole(arenaRound = ArenaRound(arenaRoundId = arenaRound.arenaRoundId))
                        arenaRoundHoles.value = tempList
                        arenaRound.arenaRoundHoles = tempList
                    }) {
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(SelectedBlue),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Filled.Add, "", tint = Color.White)
                        }
                    }
                }
                arenaRoundHoles.value.forEach { hole ->
                    ArenaRoundHoleItem(hole, arenaRound)
                }
            }
        }
    }
}

@Composable
fun ArenaRoundHoleItem(hole: ArenaRoundHole, arenaRound: ArenaRound) {
    var editMode by remember {
        mutableStateOf(false)
    }

    val number = remember { mutableStateOf(TextFieldValue()) }
    val text = remember { mutableStateOf("") }
    number.value = TextFieldValue(hole.order.toString())

    val parValue = remember { mutableStateOf(TextFieldValue()) }
    parValue.value = TextFieldValue(hole.parValue.toString())

    Card(
        elevation = 4.dp,
        modifier = Modifier
            .padding(top = 5.dp)
    ) {

        if (!editMode) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text("${hole.holeName} - Par ${parValue.value.text}")
                IconButton(
                    modifier = Modifier.size(20.dp),
                    onClick = { editMode = true },
                ) {
                    Icon(
                        Icons.Filled.Edit,
                        contentDescription = "Localized description"
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("${hole.holeName} - Par ${parValue.value.text}")
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = {
                            if (text.value == "") {
                                editMode = false

                            }
                        },

                        ) {
                        Icon(
                            Icons.Filled.Check,
                            contentDescription = "Localized description"
                        )
                    }
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(150.dp)
                            .padding(bottom = 10.dp)
                    ) {
                        Text(text = "Hull nummer: ")
                        TextField(
                            value = number.value,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                number.value = it
                                if (it.text != "") {
                                    hole.parValue = it.text.toInt()
                                    hole.order = it.text.toInt()
                                    hole.holeName = "Hull ${it.text}"

                                    if (parValue.value.text != "") {
                                        text.value = ""
                                    }
                                } else {
                                    text.value = "Du må fylle inn verdier i alle felt"
                                }
                            },
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.width(150.dp)
                    ) {
                        Text(text = "Par: ")
                        TextField(
                            value = parValue.value,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            onValueChange = {
                                parValue.value = it
                                if (it.text != "") {
                                    hole.parValue = it.text.toInt()
                                    if (number.value.text != "") {
                                        text.value = ""
                                    }
                                } else {
                                    text.value = "Du må fylle inn verdier i alle felt"
                                }
                            }
                        )
                    }
                }
                if (text.value != "") {
                    Text(text.value, color = Color(255, 0, 0))
                }
            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun ArenaMap(arena: Arena, modifier: Modifier, arenaViewModel: MyArenaViewModel) {
    lateinit var fusedLocationClient: FusedLocationProviderClient
    val latLngArena = remember { mutableStateOf(LatLng(arena.latitude.toDouble(), arena.longitude.toDouble())) }
    val mapView = rememberMapViewWithLifeCycle()
    val context = LocalContext.current

    val markerOptions = MarkerOptions()
        .title(arena.arenaName)

    Column(
        modifier = modifier
    ) {
        AndroidView(
            { mapView }
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.clear()
                map.uiSettings.isZoomControlsEnabled = true
                map.isMyLocationEnabled = true
                if (latLngArena.value.latitude == 0.0) {
                    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                    fusedLocationClient.lastLocation.addOnSuccessListener {
                        latLngArena.value = LatLng(it.latitude, it.longitude)
                        markerOptions.position(LatLng(it.latitude, it.longitude)).draggable(true)
                        map.addMarker(markerOptions)
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    it.latitude,
                                    it.longitude
                                ), 15f
                            )
                        )

                    }
                } else {
                    markerOptions.position(latLngArena.value).draggable(true)
                    map.addMarker(markerOptions)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngArena.value, 15f))
                }


                val markerDragListener: GoogleMap.OnMarkerDragListener =
                    object : GoogleMap.OnMarkerDragListener {
                        override fun onMarkerDragStart(p0: Marker?) {}

                        override fun onMarkerDrag(p0: Marker?) {}

                        override fun onMarkerDragEnd(markerDragEnd: Marker?) {
                            if(markerDragEnd == null) return
                            latLngArena.value = LatLng(markerDragEnd.position.latitude, markerDragEnd.position.longitude)
                            arenaViewModel.currentDroppedMarkerLocation.value = latLngArena.value
                        }
                    }
                map.setOnMarkerDragListener(markerDragListener)

            }
        }
    }
}


