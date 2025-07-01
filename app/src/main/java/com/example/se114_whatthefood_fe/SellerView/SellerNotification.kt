package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Doorbell
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.SellerView_model.SellerNotificationViewModel
import com.example.se114_whatthefood_fe.view.card.SellerNotification
import com.example.se114_whatthefood_fe.view.card.SellerNotificationCard


val HeaderTextSize = 22.sp
val White = Color.White
//
//@Composable
//fun SellerNotificationContent() {
//    var notifications by remember { mutableStateOf<List<SellerNotification>>(emptyList()) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(Color(0xFF00FF7F), Color.White)
//                )
//            )
//    ) {
//
//        // Header
//        SellerHeaderNotification()
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Nút tạo/xoá test
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(onClick = {
//                notifications = listOf(
//                    SellerNotification(
//                        title = "Đơn hàng mới",
//                        content = "Bạn vừa nhận được 1 đơn hàng mới từ khách hàng Nguyễn Văn A.",
//                        timestamp = "10:30 29/06/2025",
//                        status = false
//                    ),
//                    SellerNotification(
//                        title = "Đơn hàng đã giao",
//                        content = "Đơn hàng #002 đã được giao thành công.",
//                        timestamp = "08:15 28/06/2025",
//                        status = true
//                    )
//                )
//            }) {
//                Text("Tạo thông báo test")
//            }
//
//            Button(onClick = {
//                notifications = emptyList()
//            }) {
//                Text("Xóa")
//            }
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Nội dung
//        if (notifications.isNotEmpty()) {
//            LazyColumn(modifier = Modifier.fillMaxSize()) {
//                items(notifications) { item ->
//                    SellerNotificationCard(item = item)
//                }
//            }
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "Chưa có thông báo nào",
//                    color = Color.Black,
//                    fontSize = 20.sp,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//            }
//        }
//    }
//}

@Composable
fun SellerNotificationContent(viewModel: SellerNotificationViewModel, ) {
    val notifications by viewModel.notifications.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00FF7F), Color.White)
                )
            )
    ) {
        SellerHeaderNotification()

        Spacer(modifier = Modifier.height(8.dp))

        if (notifications.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(notifications) { item ->
                    SellerNotificationCard(
                        item = item,
                        onClick = { viewModel.onNotificationClicked(item) }
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chưa có thông báo nào",
                    color = Color.Black,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}


@Composable
fun SellerHeaderNotification(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Thông báo",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontSize = HeaderTextSize,
            color = White
        )
        IconButton(
            onClick = { /* TODO: xử lý bấm chuông nếu cần */ },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Doorbell,
                contentDescription = "Bell Icon",
                modifier = Modifier.size(30.dp),
                tint = White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SellerNotificationPreview() {
    //SellerNotificationContent()
}

