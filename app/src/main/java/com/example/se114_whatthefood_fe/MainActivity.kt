package com.example.se114_whatthefood_fe

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.se114_whatthefood_fe.SellerView.SellerAccount
import com.example.se114_whatthefood_fe.SellerView.SellerBottomBar
import com.example.se114_whatthefood_fe.SellerView.SellerHome
import com.example.se114_whatthefood_fe.SellerView.SellerManager
import com.example.se114_whatthefood_fe.SellerView.SellerNotificationContent
import com.example.se114_whatthefood_fe.SellerView_model.SellerHomeViewModel
import com.example.se114_whatthefood_fe.SellerView_model.SellerNotificationViewModel
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen

import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.model.ImageModel
import com.example.se114_whatthefood_fe.model.LocationManager
import com.example.se114_whatthefood_fe.ui.theme.DarkGreen

import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.LighterGreen
import com.example.se114_whatthefood_fe.ui.theme.MintGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen
import com.example.se114_whatthefood_fe.view.card.SellerNotification
import com.example.se114_whatthefood_fe.view.deviceScreen.AccountScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.BottomBarDeviceScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.NotificationScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.HomeScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.OrderScreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

//import com.google.accompanist.permissions.rememberMultiplePermissionsState
//import com.google.android.gms.location.LocationServices
//import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "user_pref")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Cho phép Compose vẽ dưới system bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val screenRootHaveBottomBar =
            listOf("Home", "Account", "Orders", "Notifications", "Favorites")
        setContent {
//            val navController = rememberNavController()
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
//            val authModel = remember {
//                AuthModel(
//                    api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//            val imageModel = remember {
//                ImageModel(
//                    api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//
//            val authViewModel =
//                remember { AuthViewModel(authModel = authModel, imageModel = imageModel) }
//
//            val foodModel = remember {
//                FoodModel(
//                    api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//            val foodViewModel = remember {
//                FoodViewModel(foodModel = foodModel)
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(Brush.verticalGradient(colors = listOf(LightGreen, MintGreen)))
//                    .systemBarsPadding()
//            ) {
//                Scaffold(
//                    containerColor = Color.Transparent,
//                    bottomBar = {
//                        if (checkHaveBottomBar(currentRoute, screenRootHaveBottomBar)) {
//                            BottomBarDeviceScreen(
//                                navController = navController,
//                                currentRoute = currentRoute
//                            )
//                        }
//                    }) { innerPadding ->
//                    NavHost(
//                        navController = navController,
//                        startDestination = ScreenRoute.HomeScreen,
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//                        composable(ScreenRoute.AccountScreen) { AccountScreen(authViewModel = authViewModel,
//                                                              navController = navController) }
//                        composable(ScreenRoute.OrderScreen) { OrderScreen(OrderViewModel()) }
//                        composable(ScreenRoute.NotificationScreen) { NotificationScreen() }
//                        composable(ScreenRoute.HomeScreen) {
//                            //test home screen
//                            //HomeScreenTest(foodViewModel = foodViewModel)
//                            HomeScreen(foodViewModel = foodViewModel)
//                        }
//                        composable(ScreenRoute.LoginOrRegisterScreen) {
//                            AuthScreen(authViewModel = authViewModel,
//                                navController = navController)}
//
//                    }
//                }
//            }

            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                bottomBar = {
                    SellerBottomBar(
                        navController = navController,
                        currentRoute = currentRoute
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "SellerHome",
                    modifier = Modifier
                        .padding(innerPadding)
                        .systemBarsPadding()
                ) {
                    composable("SellerHome") {
                        SellerHome(SellerHomeViewModel())
                    }
                    composable("SellerAccount") {
                        SellerAccount()
                    }
                    composable("SellerManager")
                    {
                        SellerManager()
                    }
                    composable("SellerNotification")
                    {
                        SellerNotificationContent(SellerNotificationViewModel())
                    }
                }
            }

        }
    }
}

fun checkHaveBottomBar(route: String?, listScreenRoot: List<String>): Boolean {
    listScreenRoot.forEach { screenRoot ->
        if (route.equals(screenRoot)) {
            return true
        }
    }
    return false
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    Button(
        onClick = { /* Do something */ },
        modifier = modifier.padding(top = 16.dp)
    ) {
        Text(text = "Click Me")
    }
}