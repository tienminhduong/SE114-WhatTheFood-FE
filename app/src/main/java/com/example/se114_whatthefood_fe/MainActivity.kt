package com.example.se114_whatthefood_fe

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.se114_whatthefood_fe.Constant.AppInfo
import com.example.se114_whatthefood_fe.SellerView.SellerAccount
import com.example.se114_whatthefood_fe.SellerView.SellerAccountScreen
import com.example.se114_whatthefood_fe.SellerView.SellerBottomBar
import com.example.se114_whatthefood_fe.SellerView.SellerHome
import com.example.se114_whatthefood_fe.SellerView.SellerManager
import com.example.se114_whatthefood_fe.SellerView.SellerNotificationContent
import com.example.se114_whatthefood_fe.SellerView_model.SellerHomeViewModel
import com.example.se114_whatthefood_fe.SellerView_model.SellerManagerViewModel
import com.example.se114_whatthefood_fe.SellerView_model.SellerNotificationViewModel
import com.example.se114_whatthefood_fe.data.remote.RetrofitInstance
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.model.CartModel
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.model.ImageModel
import com.example.se114_whatthefood_fe.model.OrderModel
import com.example.se114_whatthefood_fe.model.RatingModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.MintGreen
import com.example.se114_whatthefood_fe.view.ScaffoldRoute
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.SellerRoute
import com.example.se114_whatthefood_fe.view.authScreen.AuthScreen
import com.example.se114_whatthefood_fe.view.cart.CartScreen
import com.example.se114_whatthefood_fe.view.detailFoodScreen.DetailFoodScreen
import com.example.se114_whatthefood_fe.view.detailOrderScreen.DetailOrderScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.AccountScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.BottomBarDeviceScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.HomeScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.NotificationScreen
import com.example.se114_whatthefood_fe.view.deviceScreen.OrderScreen
import com.example.se114_whatthefood_fe.view.map.MapScreen
import com.example.se114_whatthefood_fe.view_model.AuthViewModel
import com.example.se114_whatthefood_fe.view_model.CartViewModel
import com.example.se114_whatthefood_fe.view_model.FoodDetailViewModel
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.example.se114_whatthefood_fe.view_model.MapViewModel
import com.example.se114_whatthefood_fe.view_model.NotiViewModel
import com.example.se114_whatthefood_fe.view_model.OrderDetailViewModel
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.here.sdk.core.engine.AuthenticationMode
import com.here.sdk.core.engine.SDKNativeEngine
import com.here.sdk.core.engine.SDKOptions
import com.here.sdk.core.errors.InstantiationErrorException
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPaySDK

val Context.dataStore by preferencesDataStore(name = "user_pref")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    @SuppressLint("ViewModelConstructorInComposable", "CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        askNotificationPermission()

        // Initialize ZaloPay SDK
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX)

        initializeHERESDK()

        // Cho phép Compose vẽ dưới system bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val navController = rememberNavController()
            val authModel = remember {
                AuthModel(
                    api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }
            val imageModel = remember {
                ImageModel(
                    api = RetrofitInstance.instance,
                    dataStore = dataStore
                )
            }
            val authViewModel =
                remember { AuthViewModel(authModel = authModel, imageModel = imageModel) }
            // viết ở đây
            NavHost(
                navController = navController,
                startDestination = ScaffoldRoute.LoginOrRegisterScaffold
            ) {
                composable(ScaffoldRoute.LoginOrRegisterScaffold) {
                    AuthScreen(authViewModel = authViewModel, navController = navController)
                }

                composable(ScaffoldRoute.UserScaffold) {
                    UserScaffold(
                        dataStore = dataStore,
                        authViewModel = authViewModel,
                        navControllerWhenLogout = navController
                    )
                }

                composable(ScaffoldRoute.SellerScaffold) {
                    SellerScaffold(
                        dataStore = dataStore,
                        authViewModel = authViewModel,
                        navControllerWhenLogout = navController
                    )
                }


            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
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

    private fun initializeHERESDK() {
        // Set your credentials for the HERE SDK.
        val accessKeyID = BuildConfig.HERE_API_KEY
        val accessKeySecret = BuildConfig.HERE_API_SECRET_KEY

        Log.d("HERE SDK", accessKeyID)
        Log.d("HERE SDK", accessKeySecret)

        val authenticationMode = AuthenticationMode.withKeySecret(accessKeyID, accessKeySecret)
        val options = SDKOptions(authenticationMode)
        try {
            val context = this
            SDKNativeEngine.makeSharedInstance(context, options)
        } catch (e: InstantiationErrorException) {
            throw RuntimeException("Initialization of HERE SDK failed: " + e.error.name)
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
fun SellerScaffold(dataStore: DataStore<Preferences>,
                   authViewModel: AuthViewModel,
                   navControllerWhenLogout: NavHostController) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val foodModel = remember {
        val api = RetrofitInstance.instance
        FoodModel(api, dataStore)
    }
    val sellerHomeViewModel = remember { SellerHomeViewModel(foodModel, sellerId = 1) }

    val orderModel = remember {
        val api = RetrofitInstance.instance
        OrderModel(api, dataStore)
    }
    val sellerManagerViewModel = remember {
        SellerManagerViewModel(orderModel, sellerId = 1)
    }

    val api = RetrofitInstance.instance
    val sellerNotificationViewModel = remember {
        SellerNotificationViewModel(api = api, dataStore = dataStore)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(LightGreen, MintGreen)))
            .systemBarsPadding()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            modifier = Modifier.systemBarsPadding(),
            bottomBar = {
                SellerBottomBar(
                    navController = navController,
                    currentRoute = currentRoute
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = SellerRoute.HomeScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(SellerRoute.HomeScreen) {
                    SellerHome(viewModel = sellerHomeViewModel)
                }
                composable(SellerRoute.AccountScreen) {
                    SellerAccountScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                        navControllerWhenLogout = navControllerWhenLogout
                    )
                }
                composable(SellerRoute.ManagerScreen)
                {
                    SellerManager(viewModel = sellerManagerViewModel)
                }
                composable(SellerRoute.NotificationScreen)
                {
                    SellerNotificationContent(viewModel = sellerNotificationViewModel)
                }
            }
        }
    }
}

@Composable
fun UserScaffold(
    dataStore: DataStore<Preferences>,
    authViewModel: AuthViewModel,
    navControllerWhenLogout: NavHostController
) {
    val screenRootHaveBottomBar = remember {
        listOf("Home", "Account", "Cart", "Orders", "Notifications")
    }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val mapViewModel = remember { MapViewModel() }
    val currentRoute = navBackStackEntry?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colors = listOf(LightGreen, MintGreen)))
            .systemBarsPadding()
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                if (checkHaveBottomBar(currentRoute, screenRootHaveBottomBar)) {
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
                composable(ScreenRoute.AccountScreen) {
                    AccountScreen(
                        authViewModel = authViewModel,
                        navController = navController,
                        navControllerWhenLogout = navControllerWhenLogout
                    )
                }
                composable(ScreenRoute.OrderScreen) {
                    val orderViewModel = remember {
                        OrderViewModel(
                            orderModel = OrderModel(
                                api = RetrofitInstance.instance,
                                dataStore = dataStore
                            )
                        )
                    }
                    OrderScreen(
                        orderViewModel = orderViewModel,
                        navController = navController
                    )
                }
                composable(ScreenRoute.NotificationScreen) {
                    val notiViewModel = remember {
                        NotiViewModel(RetrofitInstance.instance, dataStore)
                    }
                    NotificationScreen(viewModel = notiViewModel)
                }
                composable(ScreenRoute.CartScreen) {
                    val cartModel = remember {
                        CartModel(
                            api = RetrofitInstance.instance,
                            dataStore = dataStore
                        )
                    }
                    val cartViewModel = remember {
                        CartViewModel(cartModel = cartModel)
                    }
                    CartScreen(
                        cartViewModel = cartViewModel,
                        navController = navController
                    )
                }
                composable(ScreenRoute.HomeScreen) {
                    //test home screen
                    //HomeScreenTest(foodViewModel = foodViewModel)
                    val foodModel = remember {
                        FoodModel(
                            api = RetrofitInstance.instance,
                            dataStore = dataStore
                        )
                    }
                    val foodViewModel = remember {
                        FoodViewModel(foodModel = foodModel)
                    }

                    HomeScreen(
                        foodViewModel = foodViewModel,
                        navController = navController
                    )
                }
                ScreenRoute.LoginOrRegisterScreen
//                composable(ScreenRoute.LoginOrRegisterScreen) {
//                    AuthScreen(authViewModel = authViewModel,
//                        navController = navController)}
                composable(
                    ScreenRoute.DetailOrderScreen,
                    arguments = listOf(
                        navArgument("orderId") { type = NavType.IntType }
                    )) { backStackEntry ->
                    val orderDetailViewModel = remember {
                        OrderDetailViewModel(
                            orderModel = OrderModel(
                                api = RetrofitInstance.instance,
                                dataStore = dataStore
                            )
                        )
                    }
                    val orderId = backStackEntry.arguments?.getInt("orderId")
                    DetailOrderScreen(
                        orderDetailViewModel = orderDetailViewModel,
                        orderId = orderId,
                        navController = navController
                    )
                }
                composable(
                    ScreenRoute.DetailFoodItemScreen,
                    arguments = listOf(
                        navArgument("foodItemId") { type = NavType.IntType }
                    )) { backStackEntry ->
                    val foodDetailViewModel = remember {
                        FoodDetailViewModel(
                            foodModel = FoodModel(
                                api = RetrofitInstance.instance,
                                dataStore = dataStore
                            ),
                            ratingModel = RatingModel(
                                api = RetrofitInstance.instance,
                                dataStore = dataStore
                            )
                        )
                    }
                    val foodItemId = backStackEntry.arguments?.getInt("foodItemId")
                    DetailFoodScreen(
                        navController = navController,
                        foodDetailViewModel = foodDetailViewModel,
                        orderId = foodItemId
                    )
                }
                composable(ScreenRoute.MapScreen){
                    MapScreen(modifier = Modifier, navHostController = navController,
                        mapViewModel = mapViewModel)
                }

            }
        }
    }
}