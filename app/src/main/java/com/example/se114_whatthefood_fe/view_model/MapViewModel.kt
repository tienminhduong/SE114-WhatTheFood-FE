package com.example.se114_whatthefood_fe.view_model

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.se114_whatthefood_fe.data.remote.Address
import com.here.sdk.core.GeoCoordinates
import com.here.sdk.core.LanguageCode
import com.here.sdk.search.Place
import com.here.sdk.search.SearchCallback
import com.here.sdk.search.SearchEngine
import com.here.sdk.search.SearchError
import com.here.sdk.search.SearchOptions

class MapViewModel {
    var currentMapLocation by mutableStateOf<Address?>(null)

    private val searchEngine = SearchEngine()

    fun getAddressForCoordinates(geoCoordinates: GeoCoordinates, onResult: (String?) -> Unit) {
        val reverseGeocodingOptions = SearchOptions().apply {
            languageCode = LanguageCode.VI_VN
            maxItems = 1
        }

        searchEngine.searchByCoordinates(
            geoCoordinates,
            reverseGeocodingOptions,
            object : SearchCallback {
                override fun onSearchCompleted(searchError: SearchError?, list: List<Place>?) {
                    if (searchError != null || list.isNullOrEmpty()) {
                        onResult(null)
                    } else {
                        onResult(list[0].address.addressText)
                    }
                }
            }
        )
    }

    fun updateMapLocation(latitude: Double, longitude: Double) {
        val geoCoordinates = GeoCoordinates(latitude, longitude)
        getAddressForCoordinates(geoCoordinates) { addressText ->
            currentMapLocation = Address(
                name = addressText ?: "Selected Location",
                latitude = latitude.toFloat(),
                longitude = longitude.toFloat()
            )
        }
    }
}