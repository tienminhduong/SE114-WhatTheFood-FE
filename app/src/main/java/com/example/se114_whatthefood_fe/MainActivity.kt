package com.example.se114_whatthefood_fe

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts

import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

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
import com.example.se114_whatthefood_fe.model.OrderModel
import com.example.se114_whatthefood_fe.model.RatingModel
import com.example.se114_whatthefood_fe.ui.theme.DarkGreen

import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.MintGreen
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen
import com.example.se114_whatthefood_fe.view.card.SellerNotification
import com.example.se114_whatthefood_fe.view.detailFoodScreen.DetailFoodScreen
import com.example.se114_whatthefood_fe.view.detailOrderScreen.DetailOrderScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.AccountScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.BottomBarDeviceScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.HomeScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.NotificationScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.HomeScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.OrderScreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.FoodDetailViewModel
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.example.se114_whatthefood_fe.view_model.OrderDetailViewModel
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

val Context.dataStore by preferencesDataStore(name = "user_pref")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askNotificationPermission()
        // Cho phép Compose vẽ dưới system bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val screenRootHaveBottomBar =
            listOf("Home", "Account", "Orders", "Notifications")
        setContent {


            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val authModel = remember{
                AuthModel(api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }
            val imageModel = remember {
                com.example.se114_whatthefood_fe.model.ImageModel(api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }


            val authViewModel = remember {AuthViewModel(authModel = authModel, imageModel = imageModel)}

            val foodModel = remember{
                FoodModel(api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }
            val foodViewModel = remember {
                FoodViewModel(foodModel = foodModel)
            }

            var isLoggedIn by rememberSaveable { mutableStateOf<Boolean?>(null) }
            LaunchedEffect(Unit) {
                // check valid token
            }

            LaunchedEffect(isLoggedIn) {
                if (isLoggedIn == null) return@LaunchedEffect
                if (isLoggedIn == true) {
                    navController.navigate(ScreenRoute.HomeScreen) {
                        popUpTo(ScreenRoute.LoginOrRegisterScreen) {
                            inclusive = true
                        }
                    }
                }
            }

            Box(modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(LightGreen, MintGreen)))
                .systemBarsPadding()) {
                Scaffold(containerColor = Color.Transparent,
                    bottomBar = {
                        if(checkHaveBottomBar(currentRoute, screenRootHaveBottomBar)) {
                            BottomBarDeviceScreen(
                                navController = navController,
                                currentRoute = currentRoute
                            )
                        }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = ScreenRoute.LoginOrRegisterScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ScreenRoute.AccountScreen) { AccountScreen(authViewModel = authViewModel,
                                                              navController = navController) }
                        composable(ScreenRoute.OrderScreen) {
                            val orderViewModel = remember {OrderViewModel(
                                orderModel = com.example.se114_whatthefood_fe.model.OrderModel(
                                    api = RetrofitInstance.instance,
                                    dataStore = dataStore
                                )
                            )}
                            OrderScreen(orderViewModel = orderViewModel, navController = navController)
                        }
                        composable(ScreenRoute.NotificationScreen) { NotificationScreen() }
                        composable(ScreenRoute.HomeScreen) {
                            //test home screen
                            //HomeScreenTest(foodViewModel = foodViewModel)
                            HomeScreen(foodViewModel = foodViewModel,
                                navController = navController)
                        }
                        composable(ScreenRoute.LoginOrRegisterScreen) {
                            AuthScreen(authViewModel = authViewModel,
                                navController = navController)}
                        composable(ScreenRoute.DetailOrderScreen,
                            arguments = listOf(
                                navArgument("orderId") { type = NavType.IntType}
                            )) { backStackEntry ->
                            val orderDetailViewModel = remember {
                                OrderDetailViewModel(orderModel = OrderModel(api = RetrofitInstance.instance,
                                dataStore = dataStore))
                            }
                            val orderId = backStackEntry.arguments?.getInt("orderId")
                            DetailOrderScreen(orderDetailViewModel = orderDetailViewModel,
                                orderId = orderId,
                                navController = navController)
                        }
                        composable(ScreenRoute.DetailFoodItemScreen,
                            arguments = listOf(
                                navArgument("foodItemId") { type = NavType.IntType}
                            )) { backStackEntry ->
                            val foodDetailViewModel = remember {
                                FoodDetailViewModel(foodModel = FoodModel(api = RetrofitInstance.instance,
                                    dataStore = dataStore),
                                    ratingModel = RatingModel(api = RetrofitInstance.instance,
                                        dataStore = dataStore))
                            }
                            val foodItemId = backStackEntry.arguments?.getInt("foodItemId")
                            DetailFoodScreen(navController = navController,
                                foodDetailViewModel = foodDetailViewModel,
                                orderId = foodItemId)
                        }

                    }
                }
            }

        }
    }
    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                // Directly ask for the permission
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
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