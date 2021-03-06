package no.usn.mob3000_disky.ui.screens.round

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavHostController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.*
import com.google.gson.Gson
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.model.markerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.model.Arena
import no.usn.mob3000_disky.ui.screens.round.nav.RoundNavItem

@SuppressLint("MissingPermission")
@Composable
fun AddArenaMap(modifier: Modifier = Modifier, arenas: List<Arena>, navController: NavHostController) {
    val mapView = rememberMapViewWithLifeCycle()

    Column(
        modifier = modifier
    ) {
        AndroidView(
            {mapView}
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()

                val latLngNorway = LatLng(59.34671631717139, 10.28073959558217)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNorway, 7f))
                for (arena in arenas){
                    val latLng = LatLng(arena.latitude, arena.longitude)
                    val markerOptions = MarkerOptions()
                        .title(arena.arenaName)
                        .snippet("Antall baner: ${arena.rounds?.size}")
                        .snippet("Trykk for ?? velge").icon(BitmapDescriptorFactory.defaultMarker(
                            BitmapDescriptorFactory.HUE_AZURE))
                        .position(latLng).draggable(true)
                    map.addMarker(markerOptions).tag = arena

                }

                map.isMyLocationEnabled = true

                map.setOnInfoWindowClickListener {
                    val arenaJson = Gson().toJson(it.tag)
                    navController.navigate(RoundNavItem.ChooseTrack.route.plus("/$arenaJson"))
                }


            }
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
fun ShowArenaMap(modifier: Modifier = Modifier, arena: Arena, navController: NavHostController) {
    val mapView = rememberMapViewWithLifeCycle()

    Column(
        modifier = modifier
    ) {
        AndroidView(
            {mapView}
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()

                map.uiSettings.isZoomControlsEnabled = false
                val latLngNorway = LatLng(arena.latitude, arena.longitude)
                val markerOptions = MarkerOptions()
                    .title(arena.arenaName)
                    .position(latLngNorway)
                map.addMarker(markerOptions)

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngNorway, 15f))

                map.isMyLocationEnabled = true
            }
        }
    }
}


//below is boilerplate code to make Composable stateful, source: https://medium.com/geekculture/google-maps-in-jetpack-compose-android-ae7b1ad84e9

@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = com.google.maps.android.ktx.R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }
