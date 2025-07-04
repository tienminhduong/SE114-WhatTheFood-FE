package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.data.remote.Notification

@Composable
fun SellerNotificationDetailScreen(
    notification: Notification,
    onBack: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 0.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onBack,
//            modifier = Modifier
//                .padding(start = 16.dp, top = 12.dp)
//                .size(36.dp) // Kích thước gọn gàng
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Quay lại",
                modifier = Modifier.size(30.dp),
                tint = Color(0xFFFFFFFF)
            )
        }


        Text(
            text = "Thông báo",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = HeaderTextSize,
            color = White,
            modifier = Modifier.weight(1f) // chiếm phần còn lại để căn giữa
        )

//        Spacer(modifier = Modifier.width(64.dp)) // tạo khoảng trắng để cân nút "Quay lại"

        IconButton(
            onClick = { /* TODO: xử lý bấm chuông nếu cần */ },
        ) {
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "Bell Icon",
                modifier = Modifier.size(30.dp),
                tint = White
            )
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    listOf(Color(0x09B256FF), Color(0xFFD7F6DF))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Chi tiết thông báo",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp
                ),
                textAlign = TextAlign.Center
            )
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Tiêu đề:", fontWeight = FontWeight.Bold)
                    Text(notification.title)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Nội dung:", fontWeight = FontWeight.Bold)
                    Text(notification.content)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Thời gian:", fontWeight = FontWeight.Bold)
                    Text(notification.dateTime)
                }
            }

        }

    }
}
