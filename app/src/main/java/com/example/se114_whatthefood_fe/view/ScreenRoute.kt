package com.example.se114_whatthefood_fe.view

object ScreenRoute {
    val HomeScreen = "Home"
    val OrderScreen = "Orders"
    val NotificationScreen = "Notifications"
    val AccountScreen = "Account"
    val LoginOrRegisterScreen = "LoginOrRegister"
    val DetailOrderScreen = "DetailOrder/{orderId}"
    val DetailFoodItemScreen = "DetailFoodItem/{foodItemId}"
    val SearchScreen = "SearchResultScreen"
    val CartScreen = "Cart"
    val MapScreen = "Map"
    val ConfirmOrderScreen = "ConfirmOrder/{restaurantId}"
    val CommentScreen = "Comment/{shippingId}"
}

object SellerRoute {
    val HomeScreen = "SellerHome"
    val ManagerScreen = "SellerManager"
    val NotificationScreen = "SellerNotification"
    val AccountScreen = "SellerAccount"
    val RatedScreen = "SellerRated"
}

object ScaffoldRoute {
    val UserScaffold = "UserScaffold"
    val SellerScaffold = "SellerScaffold"
    val LoginOrRegisterScaffold = "LoginOrRegisterScaffold"
}
