package com.example.se114_whatthefood_fe.view.cart

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.se114_whatthefood_fe.data.remote.CartItem
import com.example.se114_whatthefood_fe.data.remote.FoodCategory
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import com.example.se114_whatthefood_fe.data.remote.ShippingInfoDetail
import com.example.se114_whatthefood_fe.view.ScreenRoute
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view.detailFoodScreen.GetImageFromURL
import com.example.se114_whatthefood_fe.view.detailOrderScreen.MoneyFormat
import com.example.se114_whatthefood_fe.view.pullToRefreshLazyColumn.PullToRefreshLazyColumn
import com.example.se114_whatthefood_fe.view_model.CartViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun CartScreenPreview() {

}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CartScreen(cartViewModel: CartViewModel, navController: NavHostController){
    val listCartItems by cartViewModel.listItemsInCart

    LaunchedEffect(Unit) {
        cartViewModel.loadListItemsInCart()
    }
    var isRefreshing by remember{ mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val refreshState = rememberPullToRefreshState()
    Column(modifier = Modifier.fillMaxSize()
        .padding(vertical = 16.dp)){
        TopBar(modifier = Modifier.fillMaxWidth())
//        PullToRefreshBox(
//            state = refreshState,
//            isRefreshing = isRefreshing,
//            onRefresh = {
//
//                coroutineScope.launch {
//                    isRefreshing = true
//                    cartViewModel.loadListItemsInCart()
//                    isRefreshing = false
//                }
//
//            }
//        ) {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(items = listCartItems){
//                    OrderInCart(cartItem = it)
//                }
//            }
//        }
        PullToRefreshLazyColumn<CartItem>(
            items = listCartItems,
            content = { item -> OrderInCart(cartItem = item, cartViewModel = cartViewModel,
                navController = navController)},
            onRefresh = {
                coroutineScope.launch {
                    isRefreshing = true
                    cartViewModel.loadListItemsInCart()
                    isRefreshing = false
                }
                        },
            modifier = Modifier,
            isRefreshing = isRefreshing)
    }

}

@Composable
fun TopBar(modifier: Modifier = Modifier){
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth().background(color = Color.Transparent)){
        Text(text = "Giỏ hàng",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White)
    }
}


@Composable
fun OrderInCart(cartItem: CartItem,
                cartViewModel: CartViewModel,
                navController: NavHostController? = null){
    Column(modifier = Modifier.padding(10.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .background(Color.White)){
        //ten nha hang
        Text(text = cartItem.restaurant.name,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(5.dp))
        // tong tien
        val moneyFormat = MoneyFormat(cartItem.totalAmount)
        Text(text = "Tổng tiền: $moneyFormat",
            modifier = Modifier.padding(start = 10.dp))
        // list cac mon an
        for(item in cartItem.orderDetails){
            LineFoodItem(foodItem = item, modifier = Modifier.padding(start = 10.dp))
        }
        Spacer(modifier = Modifier.height(5.dp))
        // row cos 2 nut
        // chi tiet va thanh toan
        Row(modifier = Modifier.padding(horizontal = 10.dp ))
        {
            Spacer(modifier = Modifier.weight(1f))
            // chi tiet button
            Button(onClick = {
                navController?.navigate("ConfirmOrder/${cartItem.restaurant.id}")
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0Xff00a341),
                    contentColor = Color.White),
                shape = RoundedCornerShape(10.dp)
            )
            {
                Text(text = "Chi tiết")
            }
            Spacer(modifier = Modifier.width(10.dp))
            //  xoa button
            val context = LocalContext.current
            Button(onClick = {

                cartViewModel.viewModelScope.launch {
                    val result = cartViewModel.deleteItemInCart(cartItem.restaurant.id)
                    Toast.makeText(context, if(result) "Xóa thành công"
                    else
                    "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEE636D),
                    contentColor = Color.White),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.padding(bottom = 10.dp)
                ){
                Text(text = "Xóa đơn hàng")
            }

        }
    }
}

@Composable
fun LineFoodItem(foodItem: ShippingInfoDetail, modifier: Modifier = Modifier){
    val context = LocalContext.current
    Row(modifier = modifier.fillMaxWidth()
        .padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center){
        GetImageFromURL(url = foodItem.foodItem.cldnrUrl?:"",
                        context = context)
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = foodItem.foodItem.foodName)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "x" + foodItem.amount.toString(),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 20.dp))
    }
}