package no.usn.mob3000_disky.ui.screens.MyArenas

import android.annotation.SuppressLint
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.model.*
import com.google.maps.android.ktx.awaitMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import no.usn.mob3000_disky.ui.screens.round.rememberMapViewWithLifeCycle
import no.usn.mob3000_disky.ui.theme.BtnAcceptGreen

@SuppressLint("MissingPermission")
@Composable
fun ArenaHoleMapEditor(myArenaViewModel: MyArenaViewModel) {

    val MARKER_STARTTAG = 0
    val MARKER_ENDTAG = 1

    val currentArena = myArenaViewModel.currentArena.value
    val currentHole = myArenaViewModel.currentArenaHole.value

    val startLatLngHole = remember { mutableStateOf(LatLng(currentHole.startLatitude, currentHole.startLongitude)) }
    val endLatLngHole = remember { mutableStateOf(LatLng(currentHole.endLatitude, currentHole.endLongitude)) }

    val mapView = rememberMapViewWithLifeCycle()

    val startDragMarker = remember { mutableStateOf(
        if(currentHole.startLatitude == 0.0){
            LatLng(currentArena.latitude.minus(0.001), currentArena.longitude)
        }else{
            LatLng(currentHole.startLatitude, currentHole.startLongitude)
        }
    ) }
    val endDragMarker = remember { mutableStateOf(
        if(currentHole.endLatitude == 0.0){
            LatLng(currentArena.latitude.minus(0.001), currentArena.longitude)
        }else{
            LatLng(currentHole.endLatitude, currentHole.endLongitude)
        }
    ) }

    val markerOptionsStart = MarkerOptions()
        .title("Start").icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_GREEN))

    val markerOptionsEnd = MarkerOptions()
        .title("Slutt").icon(
            BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED))

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    currentHole.startLatitude = startDragMarker.value.latitude
                    currentHole.startLongitude = startDragMarker.value.longitude

                    currentHole.endLatitude = endDragMarker.value.latitude
                    currentHole.endLongitude = endDragMarker.value.longitude


                },
                backgroundColor = BtnAcceptGreen,
                contentColor = Color.White,
            ) {
                Icon(Icons.Filled.Save, "")
            }
        },
        floatingActionButtonPosition = FabPosition.Center

    ) {
        AndroidView(
            { mapView }
        ) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.clear()
                map.uiSettings.isZoomControlsEnabled = true
                map.isMyLocationEnabled = true

                if(startLatLngHole.value.latitude == 0.0 && endLatLngHole.value.latitude == 0.0){
                    startLatLngHole.value = LatLng(currentArena.latitude.minus(0.001), currentArena.longitude)
                    endLatLngHole.value = LatLng(currentArena.latitude.minus(0.001), currentArena.longitude.minus(0.001))
                    markerOptionsStart.position(startLatLngHole.value)
                    markerOptionsEnd.position(endLatLngHole.value)
               }else{
                    markerOptionsStart.position(startLatLngHole.value).draggable(true)
                    markerOptionsEnd.position(endLatLngHole.value).draggable(true)
                }

                map.addMarker(markerOptionsStart).tag = MARKER_STARTTAG
                map.addMarker(markerOptionsEnd).tag = MARKER_ENDTAG
                map.addPolyline(
                    PolylineOptions().add(
                        markerOptionsStart.position,
                        markerOptionsEnd.position
                    )
                ).color = Color(85, 85, 85, 255).hashCode()

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(startLatLngHole.value, 15f))

                val markerDragListener: GoogleMap.OnMarkerDragListener =
                    object : GoogleMap.OnMarkerDragListener {
                        override fun onMarkerDragStart(p0: Marker?) {}

                        override fun onMarkerDrag(p0: Marker?) {

                        }

                        override fun onMarkerDragEnd(draggedMarker: Marker?) {
                            when(draggedMarker?.tag){
                                MARKER_STARTTAG -> {
                                    startDragMarker.value = draggedMarker.position
                                    map.clear()
                                    markerOptionsStart.position(draggedMarker.position).draggable(true)
                                    markerOptionsEnd.position(endDragMarker.value).draggable(true)
                                }
                                MARKER_ENDTAG -> {
                                    endDragMarker.value = draggedMarker.position
                                    map.clear()
                                    markerOptionsEnd.position(draggedMarker.position).draggable(true)
                                    markerOptionsStart.position(startDragMarker.value).draggable(true)
                                }
                            }
                            map.addMarker(markerOptionsStart).tag = MARKER_STARTTAG
                            map.addMarker(markerOptionsEnd).tag = MARKER_ENDTAG
                            map.addPolyline(
                                PolylineOptions().add(
                                    markerOptionsStart.position,
                                    markerOptionsEnd.position
                                )
                            ).color = Color(85, 85, 85, 255).hashCode()

                        }
                    }

                map.setOnMarkerDragListener(markerDragListener)

            }
        }
    }

}