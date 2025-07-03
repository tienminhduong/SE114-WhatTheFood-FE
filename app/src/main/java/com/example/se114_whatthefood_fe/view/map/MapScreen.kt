package com.example.se114_whatthefood_fe.view.map

import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.view_model.MapViewModel
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapview.MapScheme
import com.here.sdk.mapview.MapView

@Composable
fun MapScreen(
    modifier: Modifier,
    mapViewModel: MapViewModel,
    navHostController: NavHostController
) {
    Column (
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            MapViewContainer(Modifier.fillMaxSize(), mapViewModel)
        }
        Button(
            onClick = {
                navHostController.popBackStack()
                val currentLocation = mapViewModel.currentMapLocation
                Log.d("Hello tui map", "Current Location: ${currentLocation?.latitude}, ${currentLocation?.longitude}")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Confirm")
        }
    }
}