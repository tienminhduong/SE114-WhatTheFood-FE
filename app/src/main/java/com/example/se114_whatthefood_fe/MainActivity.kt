package com.example.se114_whatthefood_fe

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.example.se114_whatthefood_fe.SellerView_model.SellerManagerViewModel
import com.example.se114_whatthefood_fe.SellerView_model.SellerNotificationViewModel
import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.model.FoodModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

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
//
//
//            val navController = rememberNavController()
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
//            val authModel = remember{
//                AuthModel(api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//            val imageModel = remember {
//                com.example.se114_whatthefood_fe.model.ImageModel(api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//
//
//            val authViewModel = remember {AuthViewModel(authModel = authModel, imageModel = imageModel)}
//
//            val foodModel = remember{
//                FoodModel(api = RetrofitInstance.instance,
//                    dataStore = dataStore
//                )
//            }
//            val foodViewModel = remember {
//                FoodViewModel(foodModel = foodModel)
//            }
//
//            var isLoggedIn by rememberSaveable { mutableStateOf<Boolean?>(null) }
//            LaunchedEffect(Unit) {
//                // check valid token
//            }
//
//            LaunchedEffect(isLoggedIn) {
//                if (isLoggedIn == null) return@LaunchedEffect
//                if (isLoggedIn == true) {
//                    navController.navigate(ScreenRoute.HomeScreen) {
//                        popUpTo(ScreenRoute.LoginOrRegisterScreen) {
//                            inclusive = true
//                        }
//                    }
//                }
//            }
//
//            Box(modifier = Modifier.fillMaxSize()
//                .background(Brush.verticalGradient(colors = listOf(LightGreen, MintGreen)))
//                .systemBarsPadding()) {
//                Scaffold(containerColor = Color.Transparent,
//                    bottomBar = {
//                        if(checkHaveBottomBar(currentRoute, screenRootHaveBottomBar)) {
//                            BottomBarDeviceScreen(
//                                navController = navController,
//                                currentRoute = currentRoute
//                            )
//                        }
//                }) { innerPadding ->
//                    NavHost(
//                        navController = navController,
//                        startDestination = ScreenRoute.LoginOrRegisterScreen,
//                        modifier = Modifier.padding(innerPadding)
//                    ) {
//                        composable(ScreenRoute.AccountScreen) { AccountScreen(authViewModel = authViewModel,
//                                                              navController = navController) }
//                        composable(ScreenRoute.OrderScreen) {
//                            val orderViewModel = remember {OrderViewModel(
//                                orderModel = com.example.se114_whatthefood_fe.model.OrderModel(
//                                    api = RetrofitInstance.instance,
//                                    dataStore = dataStore
//                                )
//                            )}
//                            OrderScreen(orderViewModel)
//                        }
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


            val foodModel = remember {
                val api = RetrofitInstance.instance
                FoodModel(api, dataStore)
            }
            val sellerHomeViewModel = remember { SellerHomeViewModel(foodModel, sellerId = 1) }



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
                        SellerHome(viewModel = sellerHomeViewModel)
                    }
                    composable("SellerAccount") {
                        SellerAccount()
                    }
                    composable("SellerManager")
                    {
                        SellerManager(
                            viewModel = SellerManagerViewModel(),
                            modifier = Modifier,
                        )
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