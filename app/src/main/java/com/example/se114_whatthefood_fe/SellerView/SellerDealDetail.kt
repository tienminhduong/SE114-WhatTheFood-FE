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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.se114_whatthefood_fe.SellerView_model.SellerManagerViewModel
import com.example.se114_whatthefood_fe.view.card.DealItem
import com.example.se114_whatthefood_fe.view.card.mapStatusToVietnamese
import java.util.UUID

@Composable
fun SellerDealDetail(
    deal: DealItem,
    viewModel: SellerManagerViewModel,
    onBack: () -> Unit,
    onAccept: () -> Unit

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Thông tin đơn hàng", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(12.dp))


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = getStatusColor(deal.status), shape = RoundedCornerShape(6.dp))
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Text(
                text = deal.status?.let { mapStatusToVietnamese(it) } ?: "Không rõ trạng thái",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailSection(title = "Thông tin vận chuyển") {
            DetailItem("Mã vận đơn", deal.id.toString())
            DetailItem("Người nhận", deal.userId ?: "N/A")
            DetailItem("SĐT", deal.userContact ?: "N/A")
            DetailItem("Địa chỉ", "Địa chỉ giả lập - bạn thêm sau")
        }

        Spacer(modifier = Modifier.height(16.dp))

        DetailSection(title = "Sản phẩm") {
            Column {
                Text(deal.title ?: "Không có tên sản phẩm", fontWeight = FontWeight.SemiBold)
                Text("Số lượng: 1")
                Text("Giá: bạn tự thêm", color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tổng thanh toán: 999.999đ", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Quay lại")
            }

            Button(
                onClick = onAccept,
                modifier = Modifier.weight(1f)
            ) {
                Text("Tiếp nhận đơn")
            }
        }
    }
}

@Composable
fun DetailSection(title: String, content: @Composable () -> Unit) {
    Column {
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        content()
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
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

@Preview(showBackground = true)
@Composable
fun SellerDealDetailPreview() {
    val sampleDeal = DealItem(
        id = UUID.randomUUID(),
        title = "Đơn hàng #001",
        status = "pending",
        userId = "Nguyễn Văn A",
        userContact = "0912345678",
        imageLink = null
    )

    SellerDealDetail(
        deal = sampleDeal,
        viewModel = viewModel(),
        onBack = {},
        onAccept = {}
    )
}