package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.se114_whatthefood_fe.view.card.SellerNotificationCard


val HeaderTextSize = 22.sp
val White = Color.White

@Composable
fun SellerNotificationContent(viewModel: SellerNotificationViewModel) {
    val state by viewModel.notificationsState

    // Gọi API khi vào màn
    LaunchedEffect(Unit) {
        viewModel.fetchNotifications()
    }

    val selectedNotification = viewModel.seeDetailNotification

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x09B256FF), Color(0xFFD7F6DF))
                )
            )
    ) {

        // Nếu đang xem chi tiết → show detail screen
        if (selectedNotification != null) {
            SellerNotificationDetailScreen(
                notification = selectedNotification,
                onBack = { viewModel.seeDetailNotification = null } // ← Thoát chi tiết
            )
        }
        // Nếu không thì show danh sách
        else {
            SellerHeaderNotification()

            Spacer(modifier = Modifier.height(8.dp))
            when {
                state.loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Đang tải thông báo...")
                    }
                }

                state.error != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.error ?: "Đã xảy ra lỗi", color = Color.Red)
                    }
                }

                state.list.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Chưa có thông báo nào", fontSize = 18.sp)
                    }
                }

                else -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.list) { item ->
                            SellerNotificationCard(
                                item = item,
                                onClick = { viewModel.onNotificationClicked(item) }
                            )
                        }
                    }
                }
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
                imageVector = Icons.Default.Notifications,
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

