package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.view.card.DealItem

@Composable
fun SellerDealDetail(
    deal: DealItem,
    onClick:    () -> Unit,
    onBack: () -> Unit,
    onAccept: () -> Unit // callback cho nút tiếp nhận
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { onClick() },
    ) {
        Text("Chi tiết đơn hàng", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Text("Mã đơn: ${deal.title}", fontSize = 18.sp)
        Text("Trạng thái: ${deal.status}", fontSize = 18.sp)
        Text("Người đặt: ${deal.userId}", fontSize = 18.sp)
        Text("Liên hệ: ${deal.userContact}", fontSize = 18.sp)
        Spacer(Modifier.height(16.dp))

        Button(onClick = onAccept) {
            Text("Tiếp nhận đơn hàng")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = onBack) {
            Text("Quay lại")
        }
    }
}
