package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Immutable
data class SellerNotification(
    val imageLink: String? = null,
    val title: String? = null,
    val status: Boolean? = false,
    val content: String? = null,
    val timestamp: String? = null
)

@Composable
fun SellerNotificationCard(item: SellerNotification, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        //colors = CardDefaults.cardColors(containerColor = Color.White)
        colors = getStatusColor(item.status)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = item.title ?: "Không có tiêu đề",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
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

