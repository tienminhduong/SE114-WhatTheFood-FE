package com.example.se114_whatthefood_fe.SellerView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.se114_whatthefood_fe.SellerView_model.SellerManagerViewModel
import com.example.se114_whatthefood_fe.view.card.DealItem
import com.example.se114_whatthefood_fe.view.card.DealItemCard


//this screen is for the deals, isApproved, onGoing, isDone.
// In short is the state of all deals we have
@Composable

@Preview
fun SellerManagerPreview() {

}

@Composable
fun SellerManager(viewModel: SellerManagerViewModel, modifier: Modifier = Modifier) {
    LaunchedEffect(Unit) {
        viewModel.loadAllDeals()
    }

    OrderStatusScreen(viewModel)
}


@Composable
fun OrderStatusScreen(viewModel: SellerManagerViewModel) {
    val tabs = listOf("Chờ xác nhận", "Đã xác nhận", "Đang giao", "Đã giao", "Đã hoàn thành")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    var selectedDeal by remember { mutableStateOf<DealItem?>(null) }

    if (selectedDeal != null) {
        SellerDealDetail(
            deal = selectedDeal!!,
            viewModel = viewModel,

            onAccept = {
                viewModel.updateDealToNextStatus(selectedDeal!!)
                selectedDeal = null
            },
            onBack = {
                selectedDeal = null
            }
        )
    } else {

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

            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent,
                contentColor = Color.Black,
                edgePadding = 16.dp,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.White
                    )
                }
            )
            {
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


            val onDealClick: (DealItem) -> Unit = { deal ->
                selectedDeal = deal
            }
            // Content for each tab
            when (selectedTabIndex) {
                0 -> PendingContent(onDealClick, viewModel)
                1 -> AprrovedContent(onDealClick, viewModel)
                2 -> DeliveringContent(onDealClick, viewModel)
                3 -> DeliveredContent(onDealClick, viewModel)
                4 -> CompletedContent(onDealClick, viewModel)
            }
        }
    }
}

@Composable
fun PendingContent(onDealClick: (DealItem) -> Unit, viewModel: SellerManagerViewModel) {

    val pendingDeals by viewModel.pendingDeals.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadAllDeals()
    }

    if (pendingDeals.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(pendingDeals) { deal ->
                DealItemCard(deal = deal, onClick = { onDealClick(deal) })
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có đơn hàng nào cần duyệt",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

//fun onDealClick(deal: DealItem) {
//
//}

@Composable
fun AprrovedContent(onDealClick: (DealItem) -> Unit, viewModel: SellerManagerViewModel) {
    val deals by viewModel.approvedDeals.collectAsState()

    if (deals.isNotEmpty()) {
        LazyColumn {
            items(deals) { deal ->
                DealItemCard(deal = deal, onClick = { onDealClick(deal) })
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


@Composable
fun DeliveringContent(onDealClick: (DealItem) -> Unit, viewModel: SellerManagerViewModel) {
    val DeliveringDeals by viewModel.deliveringDeals.collectAsState()

    if (DeliveringDeals.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(DeliveringDeals) { deal ->
                DealItemCard(deal = deal, onClick = { onDealClick(deal) })
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
fun DeliveredContent(onDealClick: (DealItem) -> Unit, viewModel: SellerManagerViewModel) {
    val deliveredDeals by viewModel.deliveredDeals.collectAsState()

    if (deliveredDeals.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(deliveredDeals) { deal ->
                DealItemCard(deal = deal, onClick = { onDealClick(deal) })
            }
        }
    } else {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Chưa có đơn hàng nào đã giao",
                color = Color.Black,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun CompletedContent(onDealClick: (DealItem) -> Unit, viewModel: SellerManagerViewModel) {
    val compeletedDeals by viewModel.completedDeals.collectAsState()

    if (compeletedDeals.isNotEmpty()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(compeletedDeals) { deal ->
                DealItemCard(deal = deal, onClick = { onDealClick(deal) })
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

