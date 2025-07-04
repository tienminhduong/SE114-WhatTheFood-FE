package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.se114_whatthefood_fe.data.remote.Address
import com.example.se114_whatthefood_fe.data.remote.Restaurant
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char

object StatusOrder{
    val Pending = "Pending"
    val Approved = "Approved"
    val Delivering = "Delivering"
    val Delivered = "Delivered"
    val Completed = "Completed"
}

@Preview
@Composable
fun CardOrderPreview() {
//    val order = ShippingInfo(
//        id = 1,
//        orderTime = "2023-06-01T12:00:00",
//        arrivedTime = "2023-06-01T13:00:00",
//        totalPrice = 100000,
//        userNote = "Ghi chú đơn hàng",
//        restaurant = Restaurant(
//            id = 1,
//            name = "Nhà hàng Affffffffffffffff",
//            cldnrUrl = "https://example.com/image.jpg",
//            address = null
//        ),
//        status = "Đang giao",
//        paymentMethod = "Thanh toán khi nhận hàng",
//        address = Address(
//            name = "Địa chỉ giao hàng",
//            longitude = 10.7f,
//            latitude = 106.6f),
//        shippingInfoDetails = emptyList(),
//
//    )
//    CardOrder(order = order, modifier = Modifier)
}

@Composable
fun CardOrder(order: ShippingInfo, modifier: Modifier = Modifier){
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
                .data(order.restaurant.cldnrUrl)
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
        
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // ten nha hang
            Text(
                text = order.restaurant.name,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier= Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                // thoi gian
                val time = LocalDateTime.parse(order.orderTime)
                val timeFormat = LocalDateTime.Format {
                    day()
                    char('/')
                    monthNumber()
                    char('/')
                    year()
                    char(' ')
                    hour()
                    char(':')
                    minute()
                }
                val timeString = timeFormat.format(time)
                Text(text = timeString,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(
                            color = getColorOrderStatus(order.status),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                        //.align(Alignment.End)
                ) {
                    // status don hang
                    Text(
                        text = when(order.status)
                        {
                            StatusOrder.Pending -> "Chờ xác nhận"
                            StatusOrder.Approved -> "Đã xác nhận"
                            StatusOrder.Delivering -> "Đang giao"
                            StatusOrder.Delivered -> "Đã giao"
                            StatusOrder.Completed -> "Đã hoàn thành"
                            else -> "Trạng thái không xác định"
                        },
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }


            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Tổng tiền: " + order.totalPrice,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

fun getColorOrderStatus(status: String): Color{
    when(status)
    {
        StatusOrder.Pending -> return Color(0xFFD3D3D3)
        StatusOrder.Approved -> return Color(0xFFFFD700)
        StatusOrder.Delivering -> return Color(0xFF2196F3)
        StatusOrder.Delivered -> return Color(0xFF4CAF50)
        StatusOrder.Completed -> return Color(0xFF4CAF50)
        else -> return Color.Gray
    }
}