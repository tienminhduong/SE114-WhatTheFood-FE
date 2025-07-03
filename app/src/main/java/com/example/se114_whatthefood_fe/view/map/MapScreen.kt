package com.example.se114_whatthefood_fe.view.map

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.mapview.MapScheme
import com.here.sdk.mapview.MapView

@Composable
fun MapScreen(
    modifier: Modifier,
    navHostController: NavHostController
) {
    MapViewContainer(modifier)
}