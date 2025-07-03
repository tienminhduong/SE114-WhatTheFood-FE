package com.example.se114_whatthefood_fe.view.map

import android.location.Location
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.se114_whatthefood_fe.model.LocationManager
import com.google.android.gms.location.LocationServices
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapview.MapMeasure
import com.here.sdk.mapview.MapScheme
import com.here.sdk.mapview.MapView


@Composable
fun MapViewContainer(modifier: Modifier = Modifier)
{
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = remember { LocationManager(context, fusedLocationClient)}

    var location by remember { mutableStateOf<Location?>(null) }

    LaunchedEffect(Unit) {
        location = locationManager.getLocation()
        Log.i("location", "Current location: ${location?.latitude}, ${location?.longitude}")
    }

    AndroidView(
        factory = { context ->
            MapView(context).apply {
                onCreate(null) // Initialize the MapView
                onResume()
                loadMapScene({}, location?.latitude ?: 52.530932, location?.latitude ?: 13.384915)
            }
        },
        modifier = modifier,
        update = { mapView ->
            mapView.onResume()
            mapView.mapScene.loadScene(MapScheme.NORMAL_DAY) {}
        }
    )
}

fun MapView.loadMapScene(onMapLoaded: () -> Unit, latitude: Double, longitude: Double) {
    val distanceInMeters = 1000 * 10
    val mapMeasureZoom = MapMeasure(MapMeasure.Kind.DISTANCE, distanceInMeters.toDouble())
    this.camera.lookAt(GeoCoordinates(latitude, longitude), mapMeasureZoom)

    this.mapScene.loadScene(MapScheme.NORMAL_DAY) { mapError ->
        mapError?.let {
            // Handle map loading error
        } ?: onMapLoaded()
    }
}