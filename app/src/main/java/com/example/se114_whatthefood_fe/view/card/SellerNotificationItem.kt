package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    item: SellerNotification,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (item.status == false) Color(0xFFF0F0F0) else Color.White
    val titleFontWeight = if (item.status == true) FontWeight.Normal else FontWeight.Bold

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
                if (item.status == false) {
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
                text = item.timestamp ?: "Không rõ thời gian",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
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

