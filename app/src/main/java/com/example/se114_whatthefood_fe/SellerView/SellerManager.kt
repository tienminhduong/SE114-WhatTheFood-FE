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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.view.card.DealItem
import com.example.se114_whatthefood_fe.view.card.DealItemCard


//this screen is for the deals, isPreparing, onGoing, isDone.
// In short is the state of all deals we have
@Composable

@Preview
fun SellerManagerPreview() {

}

@Composable
fun SellerManager(modifier: Modifier = Modifier) {
    OrderStatusScreen()
}

@Composable
fun OrderStatusScreen() {
    val tabs = listOf("Chờ xác nhận", "Đang chuẩn bị", "Đang giao", "Đã giao")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00FF7F), Color.White)
                )
            )
    ) {
        // Tabs
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = Color.White
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content for each tab
        when (selectedTabIndex) {
            0 -> ConfirmingContent()
            1 -> PreparingContent()
            2 -> ShippingContent()
            3 -> DeliveredContent()
        }
    }
}

@Composable
fun ConfirmingContent() {
    val ConfirmingDeals = listOf(
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #001",
            status = "Chờ xác nhận",
            userId = "user_01",
            userContact = "0912345678"
        ),
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #002",
            status = "Chờ xác nhận",
            userId = "user_02",
            userContact = "0987654321"
        )
    )

    if (!ConfirmingDeals.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(ConfirmingDeals) { deal ->
                DealItemCard(deal = deal)
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có đơn hàng nào đang giao",
                color = Color.Black,
                fontSize = 20.sp
            )
        }


    }
}

@Composable
fun PreparingContent() {

    var ongoingDeals by remember { mutableStateOf<List<DealItem>>(emptyList()) }

    Column(modifier = Modifier.fillMaxSize())
    {
        // Nút thao tác
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                // Đổ dữ liệu test
                ongoingDeals = listOf(
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "001",
                        status = "Đang chuẩn bị",
                        userId = "user_01",
                        userContact = "0912345678"
                    ),
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "002",
                        status = "Đang chuẩn bị",
                        userId = "user_02",
                        userContact = "0987654321"
                    ),
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "001",
                        status = "Đang chuẩn bị",
                        userId = "user_01",
                        userContact = "0912345678"
                    ),
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "002",
                        status = "Đang chuẩn bị",
                        userId = "user_02",
                        userContact = "0987654321"
                    ),
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "001",
                        status = "Đang chuẩn bị",
                        userId = "user_01",
                        userContact = "0912345678"
                    ),
                    DealItem(
                        imageLink = "https://via.placeholder.com/150",
                        title = "002",
                        status = "Đang chuẩn bị",
                        userId = "user_02",
                        userContact = "0987654321"
                    )
                )
            }) {
                Text("Tạo dữ liệu test")
            }

            Button(onClick = {
                // Xóa dữ liệu
                ongoingDeals = emptyList()
            }) {
                Text("Xóa")
            }
        }


        //val ongoingDeals = listOf<DealItem>()
        //listOf(
//        DealItem(
//            imageLink = "https://via.placeholder.com/150",
//            title = "Đơn hàng #001",
//            status = "Đang chuẩn bị",
//            userId = "user_01",
//            userContact = "0912345678"
//        ),
//        DealItem(
//            imageLink = "https://via.placeholder.com/150",
//            title = "Đơn hàng #002",
//            status = "Đang chuẩn bị",
//            userId = "user_02",
//            userContact = "0987654321"
//        )
        //)

        if (ongoingDeals.isNotEmpty()) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(ongoingDeals) { deal ->
                    DealItemCard(deal = deal)
                }
            }
        } else {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chưa có đơn hàng nào cần chuẩn bị",
                    color = Color.Black,
                    fontSize = 20.sp
                )
            }

        }
    }
}

@Composable
fun ShippingContent() {
    val shippingDeals = listOf(
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #001",
            status = "Đang giao",
            userId = "user_01",
            userContact = "0912345678"
        ),
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #002",
            status = "Đang giao",
            userId = "user_02",
            userContact = "0987654321"
        )
    )

    if (!shippingDeals.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(shippingDeals) { deal ->
                DealItemCard(deal = deal)
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có đơn hàng nào đang giao",
                color = Color.Black,
                fontSize = 20.sp
            )
        }

    }
}

@Composable
fun DeliveredContent() {
    val deliveredDeals = listOf(
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #001",
            status = "Hoàn thành",
            userId = "user_01",
            userContact = "0912345678"
        ),
        DealItem(
            imageLink = "https://via.placeholder.com/150",
            title = "Đơn hàng #002",
            status = "Hoàn thành",
            userId = "user_02",
            userContact = "0987654321"
        )
    )

    if (!deliveredDeals.isEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(deliveredDeals) { deal ->
                DealItemCard(deal = deal)
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có đơn hàng nào hoàn thành",
                color = Color.Black,
                fontSize = 20.sp
            )
        }


    }

}