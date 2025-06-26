package com.example.se114_whatthefood_fe

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.se114_whatthefood_fe.data.remote.ApiService
import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.AccountScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.BottomBarDeviceScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.HomeScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.NotificationScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.OrderScreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val Context.dataStore by preferencesDataStore(name = "user_pref")

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Cho phép Compose vẽ dưới system bar
        WindowCompat.setDecorFitsSystemWindows(window, true)

//        val logging = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//
//        val client = OkHttpClient.Builder()
//            .addInterceptor(logging)
//            .build()
//        val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.4:5087/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
//            .build()
//        val authViewModel = AuthViewModel(authModel = AuthModel(api = retrofit.create(ApiService::class.java),
//            dataStore = dataStore
//        ))
        val screenRootHaveBottomBar = listOf("Home", "Account", "Orders", "Notifications", "Favorites")
        setContent {
            // set bottom bar cho mot so man hinh

            //HomeScreen()
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

//            val logging = HttpLoggingInterceptor().apply {
//                level = HttpLoggingInterceptor.Level.BODY
//            }
//
//            val client = OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .build()
//            val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.4:5087/api/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build()
            val authModel = remember{
                AuthModel(api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }

            val authViewModel = remember {AuthViewModel(authModel = authModel)}
            Box(modifier = Modifier.fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(LightGreen, White)))
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
                        startDestination = ScreenRoute.HomeScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(ScreenRoute.AccountScreen) { AccountScreen(authViewModel = authViewModel,
                                                              navController = navController) }
                        composable(ScreenRoute.OrderScreen) { OrderScreen(OrderViewModel()) }
                        composable(ScreenRoute.NotificationScreen) { NotificationScreen() }
                        composable(ScreenRoute.HomeScreen) { HomeScreen() }
                        composable(ScreenRoute.LoginOrRegisterScreen) {
                            AuthScreen(authViewModel = authViewModel,
                                navController = navController)}
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
}