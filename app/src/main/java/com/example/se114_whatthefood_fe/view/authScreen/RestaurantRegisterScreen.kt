package com.example.se114_whatthefood_fe.view.authScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.se114_whatthefood_fe.SellerScaffold
import com.example.se114_whatthefood_fe.SellerView_model.SellerRegisterRestaurantViewModel
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.model.ImageModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.SellerRoute
import com.example.se114_whatthefood_fe.view_model.AuthViewModel

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun RestaurantRegisterScreen() {

    //RestaurantRegisterScreen(rememberNavController())
}

@Composable
fun RestaurantRegisterScreen(
    authViewModel: AuthViewModel,
    sellerRegisterRestaurantViewModel: SellerRegisterRestaurantViewModel,
    navController: NavController
) {

    val selectedAddress = navController.currentBackStackEntryAsState().value?.savedStateHandle?.get<Address>("selectedLocation")
    if (selectedAddress == null)
        Log.d("ConfirmOrderScreen", "No selected address")
    else {
        Log.d(
            "ConfirmOrderScreen",
            "Selected address: ${selectedAddress.name}: ${selectedAddress.latitude}, ${selectedAddress.longitude}"
        )
        sellerRegisterRestaurantViewModel.restaurantAddress = selectedAddress
    }
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = listOf(LightGreen, White)))
            .systemBarsPadding(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Đăng ký nhà hàng",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 24.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            color = White,
            fontWeight = FontWeight.Bold
        )
        RoundCornerTextFieldWithIcon(
            value = sellerRegisterRestaurantViewModel.restaurantName,
            onValueChange = { sellerRegisterRestaurantViewModel.restaurantName = it },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Storefront,
                    contentDescription = "Restaurant Icon",
                    tint = LightGreen
                )
            },
            placeholder = "Tên nhà hàng",
            modifier = Modifier.padding(10.dp)
        )

        Text(
            text = "Địa chỉ nhà hàng",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            color = White,
            fontSize = 16.sp
        )
        Text(
            text = selectedAddress?.name?: "Vui lòng chọn địa chỉ từ bản đồ",
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(10.dp, 0.dp),
            color = White,
            fontSize = 16.sp
        )
        Text(
            text = if (selectedAddress != null) {
                "Tọa độ: ${selectedAddress.latitude}, ${selectedAddress.longitude}"
            } else {
                ""
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            color = White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))
        ButtonWithIcon(
            text = "Chọn địa chỉ từ bản đồ",
            onClick = { navController.navigate(SellerRoute.MapScreen) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location Icon",
                    tint = LightGreen
                )
            },
            modifier = Modifier.padding(10.dp, 0.dp)
        )

        Button(
            onClick = { sellerRegisterRestaurantViewModel.callCreateRestaurant(context) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = White)
        ) {
            Text(
                text = "Xác nhận",
                color = LightGreen,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}