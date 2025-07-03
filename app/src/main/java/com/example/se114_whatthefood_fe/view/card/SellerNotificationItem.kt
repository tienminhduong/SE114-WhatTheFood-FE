package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.data.remote.Notification
import java.util.UUID


@Immutable
data class SellerNotification(
    val id: String = UUID.randomUUID().toString(),
    val imageLink: String? = null,
    val title: String? = null,
    val status: Boolean? = false,
    val content: String? = null,
    val timestamp: String? = null
)

@Composable
fun SellerNotificationCard(
    item: Notification,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (item.isRead == false) Color(0xFFF0F0F0) else Color.White
    val titleFontWeight = if (item.isRead == true) FontWeight.Normal else FontWeight.Bold

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Chấm đỏ nếu chưa đọc
                if (item.isRead == false) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                Color.Red,
                                shape = androidx.compose.foundation.shape.CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = item.title ?: "Không có tiêu đề",
                    fontWeight = titleFontWeight,
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.content ?: "Không có nội dung",
                fontSize = 14.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = item.dateTime ?: "Không rõ thời gian",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun SellerNotificationDetailScreen(notification: Notification) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Chi tiết thông báo", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tiêu đề: ${notification.title}", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Nội dung: ${notification.content}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Thời gian: ${notification.dateTime}")
        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
fun getStatusColor(status: Boolean?): CardColors {
    return when (status) {
        true -> CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))   // did Read
        false -> CardDefaults.cardColors(containerColor = Color(0xFFD9F3B5))  // unread
        else -> CardDefaults.cardColors(containerColor = Color(0xFFD9F3B5))  // unread
    }
}

