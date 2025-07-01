package com.example.se114_whatthefood_fe.view.detailOrderScreen

import android.icu.text.DecimalFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.FoodCategory
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.data.remote.ShippingInfoDetail
import com.example.se114_whatthefood_fe.view.authScreen.ButtonIcon
import com.example.se114_whatthefood_fe.view.card.StatusOrder
import com.example.se114_whatthefood_fe.view.card.getColorOrderStatus
import com.example.se114_whatthefood_fe.view_model.OrderDetailViewModel

@Preview
@Composable
fun DetailOrderScreenPreview()
{
    val order = ShippingInfo(
        id = 1,
        orderTime = "2023-06-01T12:00:00",
        arrivedTime = "2023-06-01T13:00:00",
        totalPrice = 100000,
        userNote = "Ghi chú đơn hàng",
        restaurant = Restaurant(
            id = 1,
            name = "Nhà hàng Affffffffffffffff",
            cldnrUrl = "https://example.com/image.jpg",
            address = null
        ),
        status = "Đang giao",
        paymentMethod = "Thanh toán khi nhận hàng",
        address = Address(
            name = "Địa chỉ giao hàng",
            longitude = 10.7f,
            latitude = 106.6f),
        shippingInfoDetails = listOf(ShippingInfoDetail(
            foodItem = FoodItemResponse(
                id = 0,
                foodName = "hello 123 ",
                description = "",
                soldAmount = 1,
                available = true,
                price = 200000,
                foodCategory = FoodCategory(2,"hi 1345234"),
                restaurant = Restaurant(0,"","",null),
                cldnrUrl = ""
            ),
            amount = 1
        ))
    )
    //DetailOrderScreen(order = order)
}

@Composable
fun DetailOrderScreen(modifier: Modifier = Modifier,
                      navController: NavHostController? = null,
                      orderId: Int? = null,
                      orderDetailViewModel: OrderDetailViewModel
                      )
{
    val order =  orderDetailViewModel.order.collectAsState().value
    LaunchedEffect(orderId) {
        if (orderId != null) {
            orderDetailViewModel.LoadById(orderId)
        }
    }
    Scaffold(topBar = {
        HeaderDetailOrderScreen(navController = navController)
    },
        containerColor = Color.Transparent) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)
            .fillMaxSize()) {
            StatusOrder(order = order, modifier = Modifier.padding(10.dp))
            CardDetailOrderInfo(
                orderDetail = order?.shippingInfoDetails[0],
                modifier = Modifier.padding(10.dp))
        }

    }
}

@Composable
fun CardDetailOrderInfo(orderDetail: ShippingInfoDetail?, modifier: Modifier = Modifier)
{
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White,
                shape = RoundedCornerShape(10.dp))
            .height(IntrinsicSize.Max) // 👈 Row cao bằng nội dung cao nhất
            .padding(10.dp)
    ) {
        // anh don hang
        // image
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(orderDetail?.foodItem?.cldnrUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)  // Cache trên ổ đĩa
                .memoryCachePolicy(CachePolicy.ENABLED) // Cache trên RAM
                //.size(100, 100) // Set kích thước ảnh
                .placeholder(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .error(drawableResId = com.example.se114_whatthefood_fe.R.drawable.google__g__logo)
                .build(),
            contentDescription = "Card Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(shape= RoundedCornerShape(8.dp))
                .fillMaxHeight()            // 👈 Chiều cao bằng với Column bên cạnh
                .aspectRatio(1f)            // 👈 Tự động đặt chiều rộng = chiều cao → vuông
        )

        Spacer(modifier = Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // ten mon an
            Text(
                text = orderDetail?.foodItem?.foodName ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier= Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // ten loai mon an
            Text(
                text = orderDetail?.foodItem?.foodCategory?.name ?: "",
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier= Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            // so luong nhan don gia
            Text(
                text = MoneyFormat(orderDetail?.foodItem?.price ?: 0)
                        + "đ x " + orderDetail?.amount,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            // tong tien
            Text(
                text = MoneyFormat(orderDetail?.foodItem?.price?.times(orderDetail.amount) ?: 0) + "đ",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth())
        }
    }
}

fun MoneyFormat(money: Int): String
{
    return DecimalFormat("#,###").format(money).replace(",",".")
}

@Composable
fun StatusOrder(order: ShippingInfo?, modifier: Modifier = Modifier)
{
    Column(modifier = modifier.fillMaxWidth()
        .clip(shape = RoundedCornerShape(10.dp))
        .background(color = Color.Magenta),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        // trang thai don hang
        Box(modifier = Modifier.fillMaxWidth()
            .background(color = getColorOrderStatus(order?.status ?: "Pending"))
            .padding(5.dp)){
            Text(text =
                if(order?.status != null)
                {
                    when(order.status)
                    {
                        StatusOrder.Pending -> "Đang chờ xác nhận"
                        StatusOrder.Approved -> "Đã xác nhận"
                        StatusOrder.Delivering -> "Đang giao"
                        StatusOrder.Delivered -> "Đã giao"
                        StatusOrder.Completed -> "Đã hoàn thành"
                        else -> ""
                    }

                } else {
                    ""
                },
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold)
        }
        // dia chi nhan hang
        Column(horizontalAlignment = Alignment.Start,
            modifier = Modifier.background(color = Color.White)){
            Text(text = "Địa chỉ nhận hàng",
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
                    .padding(start = 10.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
                    .height(IntrinsicSize.Max)
            ){
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                        .fillMaxHeight()
                        .aspectRatio(1f)
                )
                Text(text = order?.address?.name ?: "",
                    fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun HeaderDetailOrderScreen(navController: NavHostController?, modifier: Modifier = Modifier)
{
    Row(verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth().background(color = Color.Transparent)){
        ButtonIcon(icon = Icons.AutoMirrored.Filled.ArrowBack,
            onClick = { navController?.popBackStack() },
            colorBackGround = Color.Transparent,
            colorIcon = Color.Black,
            modifier = modifier)
        Text(text = "Thông tin đơn hàng")
    }
}
