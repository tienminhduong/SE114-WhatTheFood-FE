package com.example.se114_whatthefood_fe.model


import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationManager(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient
)
{
    @OptIn(ExperimentalCoroutinesApi::class)
    @SuppressLint("ServiceCast")
    suspend fun getLocation(): Location?
    {
        Log.d("DEBUG", "Getting location from FusedLocationProviderClient...")

        // check quyen truy cap vi tri tuyet doi
        val hasGrantedFineLocationPermission =
            ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION ) ==PackageManager.PERMISSION_GRANTED
        // check quyen truy cap vi tri tuong doi
        val hasGrantedCoarseLocationPermission =
            ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED

        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if(!isGpsEnabled && !(hasGrantedCoarseLocationPermission || hasGrantedFineLocationPermission)) {
            // Khong co quyen truy cap vi tri
            return null
        }

        return suspendCancellableCoroutine { cont ->
            fusedLocationProviderClient.lastLocation.apply{
                if(isComplete)
                {
                    if(isSuccessful)
                        cont.resume(result)
                    else
                        cont.resume(null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    cont.resume(result)
                }
                addOnFailureListener {
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }
}