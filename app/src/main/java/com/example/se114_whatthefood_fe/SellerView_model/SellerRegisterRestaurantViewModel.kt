package com.example.se114_whatthefood_fe.SellerView_model

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.CreateRestaurantRequest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SellerRegisterRestaurantViewModel
    (val navController: NavController,
     val dataStore: DataStore<Preferences>,
    private val apiService: ApiService) : ViewModel()
{
        var restaurantName by mutableStateOf<String>("")
        var restaurantAddress by mutableStateOf<Address?>(null)
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    fun callCreateRestaurant(context: Context)
    {
        if (restaurantAddress == null) {
            Toast.makeText(context, "Vui lòng chọn địa chỉ nhà hàng", Toast.LENGTH_SHORT).show()
        }
        else if (restaurantName.isEmpty()) {
            Toast.makeText(context, "Vui lòng nhập tên nhà hàng", Toast.LENGTH_SHORT).show()
        }
        else {
            viewModelScope.launch {
                val res = CreateRestaurantRequest(name = restaurantName, address = restaurantAddress!!)
                val preferences = dataStore.data.first()
                val token = preferences[TOKEN_KEY]
                apiService.createRestaurant(token = "Bearer $token", res)
                    .let { response ->
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Đăng ký nhà hàng thành công", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        } else {
                            Log.e("CreateRestaurant", "Error creating restaurant: ${response.errorBody()?.string()}")
                            Toast.makeText(context, "Đăng ký nhà hàng thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }



}