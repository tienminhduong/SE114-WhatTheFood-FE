package com.example.se114_whatthefood_fe.view.map

import android.content.Context
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.se114_whatthefood_fe.R
import com.example.se114_whatthefood_fe.model.LocationManager
import com.google.android.gms.location.LocationServices
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.GeoCoordinatesUpdate
import com.here.sdk.gestures.TapListener
import com.here.sdk.mapview.MapCamera
import com.here.sdk.mapview.MapCameraAnimationFactory
import com.here.sdk.mapview.MapImageFactory
import com.here.sdk.mapview.MapMeasure
import com.here.sdk.mapview.MapScheme
import com.here.sdk.mapview.MapView
import com.here.time.Duration
import com.here.sdk.mapview.MapMarker


@Composable
fun MapViewContainer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = remember { LocationManager(context, fusedLocationClient) }
    var userLocation by remember { mutableStateOf<GeoCoordinates?>(null) }

    val markers = remember { mutableListOf<MapMarker>() }

    LaunchedEffect(Unit) {
        val location = locationManager.getLocation()
        location?.let {
            userLocation = GeoCoordinates(it.latitude, it.longitude)
        }
    }

    AndroidView(
        factory = { ctx ->
            MapView(ctx).apply {
                onCreate(null)
                onResume()
                loadMapScene { }
                setTapGestureHandler(this, context, markers)
            }
        },
        modifier = modifier,
        update = { mapView ->
            mapView.onResume()
            mapView.mapScene.loadScene(MapScheme.NORMAL_DAY) {
                userLocation?.let { coords ->
                    val mapMeasureZoom = MapMeasure(MapMeasure.Kind.DISTANCE, 10000.0)
                    mapView.camera.lookAt(coords, mapMeasureZoom)
                }
            }
        }
    )
}

fun MapView.loadMapScene(onMapLoaded: () -> Unit) {
    val distanceInMeters = 1000 * 10
    val mapMeasureZoom = MapMeasure(MapMeasure.Kind.DISTANCE, distanceInMeters.toDouble())
    this.camera.lookAt(GeoCoordinates(52.530932, 13.384915), mapMeasureZoom)

    this.mapScene.loadScene(MapScheme.NORMAL_DAY) { mapError ->
        mapError?.let {
            // Handle map loading error
        } ?: onMapLoaded()
    }
}


fun MapCamera.flyTo(geoCoordinates: GeoCoordinates) {
    val geoCoordinatesUpdate = GeoCoordinatesUpdate(geoCoordinates)
    val bowFactor = 0.0
    val animation = MapCameraAnimationFactory.flyTo(geoCoordinatesUpdate, bowFactor, Duration.ofMillis(100))
    this.startAnimation(animation)
}


private fun setTapGestureHandler(mapView: MapView, context: Context, markers: MutableList<MapMarker>) {
    mapView.gestures.tapListener = TapListener { touchPoint ->
        val geoCoordinates = mapView.viewToGeoCoordinates(touchPoint)
        geoCoordinates?.let {
            Log.d("Map service", "Tapped at: ${it.latitude}, ${it.longitude}")
            try {
                val mapImage = MapImageFactory.fromResource(
                    context.resources,
                    R.drawable.location_on_icon
                )

                if (markers.isNotEmpty()) {
                    // Remove existing markers
                    markers.forEach { it -> mapView.mapScene.removeMapMarker(it) }
                    markers.clear()
                }

                val mapMarker = MapMarker(it, mapImage)
                mapView.mapScene.addMapMarker(mapMarker)

                markers.add(mapMarker)

                mapView.camera.flyTo(it)
            }
            catch (e: Exception) {
                Log.e("Map service", "Error adding marker: ${e.message}")
            }
        }
    }
}

