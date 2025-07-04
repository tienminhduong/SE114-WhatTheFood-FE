package com.example.se114_whatthefood_fe.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


@Immutable
data class Product(
    val id: Int? = 0,
    val name: String? = null,
    val price: Double? = 0.0,
    val soldAmount: Int? = 0,
    val isAvailable: Boolean? = false,
    val imgUrl: String? = "",
    val categoryId: Int? = 0,
    val description: String? = "Không có mô tả chi tiết cho món ăn",
    val restaurantId: Int? = 0
)

@Composable
fun ProductItem(
    item: Product,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val name = item.name ?: "Sản phẩm không tên"
    val price = item.price ?: 0.0
    val sold = item.soldAmount ?: 0
    val available = item.isAvailable ?: false
    val imageUrl = item.imgUrl ?: "https://via.placeholder.com/100"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            // Ảnh sản phẩm
            AsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Thông tin sản phẩm
            Column(modifier = Modifier.weight(1f)) {
                // Tên và giá
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1
                    )
                    Text(
                        text = "${price.toInt()} đ",
                        color = Color(0xFF388E3C),
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "${item.description}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Medium)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Đã bán + trạng thái
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Đã bán: $sold",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                    Text(
                        text = if (available) "Còn hàng" else "Hết hàng",
                        color = if (available) Color(0xFF2E7D32) else Color.Red,
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                    )
                }
            }
        }
    }
}
