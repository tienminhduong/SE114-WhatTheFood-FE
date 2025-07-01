package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import java.util.UUID

@Immutable
data class DealItem(
    val id: UUID? = UUID.randomUUID(),
    val imageLink: String? = null,
    val title: String? = null,
    val status: String? = null,
    val userId: String? = null,
    val userContact: String? = null
)

@Composable
fun DealItemCard(
    deal: DealItem,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    deal.imageLink ?: "https://via.placeholder.com/100"
                ),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = deal.title ?: "Không có tên đơn",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .background(
                            color = getStatusColor(deal.status),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = deal.status?.let { mapStatusToVietnamese(it) }
                            ?: "Không rõ trạng thái",
                        //text = deal.status ?: "Không rõ trạng thái",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Liên hệ: ${deal.userContact ?: "N/A"}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Mã vận đơn " + deal.id,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            }
        }
    }
}

fun getStatusColor(status: String?): Color {
    return when (status) {
        "Pending" -> Color.LightGray
        "Approved" -> Color(0xFFFFA726)
        "Delivering" -> Color(0xFF29B6F6)
        "Delivered" -> Color(0xFF63C467)
        "Completed" -> Color(0xFF63C467)
        else -> Color.LightGray
    }
}

fun mapStatusToVietnamese(status: String): String {
    return when (status.lowercase()) {
        "pending", "chờ xác nhận" -> "Chờ xác nhận"
        "approved", "đã xác nhận" -> "Đã xác nhận"
        "delivering", "đang giao" -> "Đang giao"
        "delivered", "đẫ giao" -> "Đã giao"
        "completed", "hoàn thành", "delivered" -> "Đã giao"
        else -> "Không rõ"
    }
}


@Preview(showBackground = true)
@Composable
fun DealItemCardPreview() {
    DealItemCard(
        deal = DealItem(
            imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "Đơn hàng ",
            status = "Đang giao",
            userId = "01029",
            userContact = "0905283353"
        )
    )
}
