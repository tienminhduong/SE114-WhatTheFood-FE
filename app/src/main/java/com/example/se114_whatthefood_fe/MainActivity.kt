package com.example.se114_whatthefood_fe

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
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
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.BottomBarDeviceScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.NotificationScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.OrderScreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.OrderViewModel

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Cho phép Compose vẽ dưới system bar
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {

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
                    modifier = Modifier.padding(innerPadding)
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
                        SellerNotificationContent()
                    }
                }
            }
//            //HomeScreen()
//            val navController = rememberNavController()
//            val navBackStackEntry by navController.currentBackStackEntryAsState()
//            val currentRoute = navBackStackEntry?.destination?.route
//            Scaffold(bottomBar = {
//                BottomBarDeviceScreen(
//                    navController = navController,
//                    currentRoute = currentRoute
//                )
//            }) { innerPadding ->
//                NavHost(
//                    navController = navController,
//                    startDestination = "Account",
//                    modifier = Modifier.padding(innerPadding)
//                ) {
//                    composable("Account") { AuthScreen(AuthViewModel()) }
//                    composable("Orders") { OrderScreen(OrderViewModel()) }
//                    composable("Notifications") { NotificationScreen() }
//                    composable("Home") { HomeScreen() }
//                }
//            }

        }
    }
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ViewModelConstructorInComposable")
@Preview(showBackground = false)
@Composable
fun GreetingPreview() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(bottomBar = {
        BottomBarDeviceScreen(
            navController = navController,
            currentRoute = currentRoute
        )
    }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Account",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Account") { AuthScreen(AuthViewModel()) }
            composable("Orders") { OrderScreen(OrderViewModel()) }
            composable("Notifications") { NotificationScreen() }
        }
    }

}