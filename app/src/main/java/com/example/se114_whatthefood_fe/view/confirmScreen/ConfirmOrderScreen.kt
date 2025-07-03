package com.example.se114_whatthefood_fe.view.confirmScreen

import android.annotation.SuppressLint
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.CartItem
import com.example.se114_whatthefood_fe.data.remote.ShippingInfoDetail
import com.example.se114_whatthefood_fe.model.AuthModel
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view.detailOrderScreen.MoneyFormat
import com.example.se114_whatthefood_fe.view_model.ConfirmOrderViewModel
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@Composable
fun HeaderConfirmOrder(modifier: Modifier = Modifier,
                       navController: NavHostController){
    Row(modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically){
        ButtonIcon(
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { navController.popBackStack() },
            colorBackGround = Color.Transparent,
            colorIcon = Color.White,
            modifier = modifier
        )
        Text(
            text = "Xác nhận đơn hàng",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ConfirmOrderScreen(modifier: Modifier = Modifier,
                       navController: NavHostController,
                       confirmOrderViewModel: ConfirmOrderViewModel,
                       id: Int){
    val currentCartItem = confirmOrderViewModel.currentCardItem
    LaunchedEffect(id) {
        confirmOrderViewModel.getCurrentCardItem(id)
    }
    Scaffold(
        topBar = {HeaderConfirmOrder(navController = navController, modifier = Modifier)},
        bottomBar = {
            Row(modifier = Modifier.fillMaxWidth()
                .height(75.dp)
                .background(color = Color.White)){
                Column(modifier = Modifier.weight(0.5f)
                    .padding(top = 10.dp, bottom = 10.dp,
                        start = 10.dp)){
                    Text(text = "Tổng tiền",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = LightGreen
                    )
                    Text(text = MoneyFormat(confirmOrderViewModel.currentCardItem?.totalAmount ?: 0) + "đ",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = LightGreen)
                }
                val activity = LocalActivity.current
                Box(modifier = Modifier.weight(0.5f)
                    .fillMaxHeight()
                    .background(color = LightGreen)
                    .clickable{
                        if(confirmOrderViewModel.payType == 0)
                            // call api
                            confirmOrderViewModel.createOrder()
                            else
                        {
                            confirmOrderViewModel.onPaymentClick(confirmOrderViewModel.currentCardItem?.totalAmount ?: 0,
                                activity = activity!!)
                        }
                    },
                    contentAlignment = Alignment.Center){
                    Text(text = "Xác nhận",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White)
                }
            }
        },
        containerColor = Color.Transparent
    ){
        innerPadding->
            Column(modifier = modifier.fillMaxSize()
                .padding(innerPadding)){
                if(currentCartItem == null){
                    CircularProgressIndicator()
                    return@Scaffold
                }

                // hien content
                ContentCartItem(cardItem = currentCartItem,
                    confirmOrderViewModel = confirmOrderViewModel)
            }


    }

}


@Composable
fun ContentCartItem(confirmOrderViewModel: ConfirmOrderViewModel,cardItem: CartItem, modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxWidth()
        .padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)){
        // card nha hang
        Column(modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .padding(10.dp)
            .fillMaxWidth()){
            Text(text = cardItem.restaurant.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color.Red)
                Text(text = cardItem.restaurant.address?.name.toString(),
                    fontSize = 20.sp)
            }
        }
        // card dia chi nhan hang
        Column(modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
            .background(color = Color.White)
            .fillMaxWidth()
            .padding(10.dp)
            .clickable{
            // add ham qua map
        }){
            Text(text = "Địa chỉ nhận hàng",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween){
                Icon(imageVector = Icons.Filled.LocationOn,
                    contentDescription = null,
                    tint = Color.Red)
                Text(text = cardItem.restaurant.address?.name.toString(),
                    fontSize = 20.sp)
            }
        }
        var payType by remember { mutableIntStateOf(0) }
        Column(horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .padding(10.dp)){
            Text(text = "Phương thức thanh toán",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                RadioButton(selected = confirmOrderViewModel.payType == 0,
                    onClick = {
                        confirmOrderViewModel.payType = 0
                    })
                Text(text = "Tiền mặt")
            }
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                RadioButton(selected = confirmOrderViewModel.payType == 1,
                    onClick = {
                        confirmOrderViewModel.payType = 1
                    })
                Text(text = "Chuyển khoản qua Zalopay")
            }
        }

        TextField(value = confirmOrderViewModel.description,
            onValueChange = {
                confirmOrderViewModel.description = it
            },
            placeholder = {Text("Ghi chú",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(focusedContainerColor =  Color.White,
                unfocusedContainerColor = Color.White)
        )

        cardItem.orderDetails.forEach {
            LineCardFoodItem(item = it, confirmOrderViewModel = confirmOrderViewModel)
        }

    }
}

@Composable
fun LineCardFoodItem(modifier: Modifier = Modifier,
                     item: ShippingInfoDetail,
                     confirmOrderViewModel: ConfirmOrderViewModel){
    Row(modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
        .fillMaxWidth()
        .background(color = Color.White)
        .padding(5.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically){
        // hinh
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(item.foodItem.cldnrUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp).clip(shape= RoundedCornerShape(8.dp))
        )
        var amount by remember{ mutableStateOf(item.amount)}
        var isLoading by remember { mutableStateOf(false) }
        Spacer(modifier.width(10.dp))
        // text
        Text(text = item.foodItem.foodName)
        Spacer(modifier.width(10.dp))
        // text so luong
        Text(text = "Số lượng: ")

        // textfield
        val coroutineScope = rememberCoroutineScope()
        Row(horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
            Icon(imageVector = Icons.Default.Remove,
                contentDescription = null,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        isLoading = true
                        if (confirmOrderViewModel.updateAmount(item.foodItem.id, amount - 1))
                            amount--
                        confirmOrderViewModel.getCurrentCardItem(item.foodItem.restaurant.id)
                        isLoading = false
                    }
                }
            )

            Text(text = "${amount}")
            Icon(imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        isLoading = true
                        if (confirmOrderViewModel.updateAmount(item.foodItem.id, amount + 1))
                            amount++
                        confirmOrderViewModel.getCurrentCardItem(item.foodItem.restaurant.id)
                        isLoading = false
                    }
                }
            )
        }
    }
}