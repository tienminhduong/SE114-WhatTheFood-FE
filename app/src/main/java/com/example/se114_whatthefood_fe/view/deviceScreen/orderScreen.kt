package com.example.se114_whatthefood_fe.view.deviceScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.card.CardOrder
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import kotlinx.coroutines.launch

@SuppressLint("ViewModelConstructorInComposable")
@Composable
@Preview
fun OrderScreenPreview() {
//    val orderViewModel = OrderViewModel()
//    OrderScreen(orderViewModel)
}

@Composable
fun HeaderOrderScreen(modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center){
        Text(text = "Đơn hàng",
            textAlign = TextAlign.Center,
            fontWeight = Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontSize = HeaderTextSize,
            color = White)
    }
}

@Composable
fun Tab1(modifier: Modifier = Modifier){
    Text(text = "hello1", color = White)
}

@Composable
fun Tab2(modifier: Modifier = Modifier){
    Text(text = "hello2", color = White)
}

@Composable
fun Content(navController: NavController? = null, indexTab: Int, orderViewModel: OrderViewModel, modifier: Modifier = Modifier){
    val list = when(indexTab) {
        0->orderViewModel.pendingOrderList.items
        1->orderViewModel.deliveringOrderList.items
        2->orderViewModel.deliveredOrderList.items
        3->orderViewModel.completedOrderList.items
        4->orderViewModel.allOrderList.items
        else ->orderViewModel.allOrderList.items
    }
    LazyColumn(modifier = modifier.fillMaxSize()
        .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        itemsIndexed(list, key = { index, t -> index }) { index, item ->
            if(index >= list.size -1 && !orderViewModel.allOrderList.endReached && !orderViewModel.allOrderList.isLoading){
                FetchData(orderViewModel, indexTab)
            }
            CardOrder(order = item,
                modifier = Modifier.clickable{
                    if(item.status == "Completed")
                        navController?.navigate("Comment/${item.id}")
                    else
                    navController?.navigate("DetailOrder/${item.id}")
                })
        }
        item{
            if(orderViewModel.allOrderList.isLoading)
            {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            }
        }
    }

}

@Composable
fun SwipeTabs(navController: NavController? = null, orderViewModel: OrderViewModel, pagerState: PagerState, modifier: Modifier = Modifier) {
    FetchData(orderViewModel = orderViewModel, indexTab = pagerState.currentPage)
    HorizontalPager(state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1) { page ->
        Content(indexTab = pagerState.currentPage, orderViewModel = orderViewModel,
            navController = navController)
    }
}

@Composable
fun OrderScreen(navController: NavController? = null, orderViewModel: OrderViewModel, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0,
        pageCount = { 5 }) // Assuming there are 5 tabs
    Column(modifier = Modifier.fillMaxSize())
    {
        // header
        HeaderOrderScreen()

//        CustomScrollTab(listOf("Đơn đã mua", "Lịch sử", "Đánh giá", "Đang đến", "Giỏ hàng"),
//           pagerState = pagerState)
        ScrollTab(listOf("Chờ xác nhận", "Đang giao", "Đã giao","Đã hoàn thành","Lịch sử"),
            pagerState = pagerState,
            modifier = Modifier.fillMaxWidth(),
            orderViewModel = orderViewModel)
        // tab content
        SwipeTabs(pagerState = pagerState, orderViewModel = orderViewModel,
            navController = navController)

    }

}

@Composable
fun ScrollTab(tabTitles: List<String>,
              pagerState: PagerState,
              modifier: Modifier = Modifier,
              orderViewModel: OrderViewModel){
    val coroutineScope = rememberCoroutineScope()
    ScrollableTabRow(selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        containerColor = Color.Transparent,
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(Modifier. tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                color = Color.White)}
    ) {
        tabTitles.forEachIndexed {
            indexTab, title ->
            Tab(
                selected = pagerState.currentPage == indexTab,
                onClick = {
                    // Handle tab click
                    // You can use pagerState.animateScrollToPage(index) to change the page
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(indexTab)

                    }
                },
                text = {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = Bold,
                        color = if (pagerState.currentPage == indexTab) Color.White else Color.White.copy(alpha = 0.6f)
                    )
                }
            )
        }
    }
}

@Composable
fun FetchData(orderViewModel: OrderViewModel, indexTab: Int)
{
    var previousTabIndex = remember { mutableIntStateOf(-1) }
    LaunchedEffect(indexTab) {
        if(indexTab != previousTabIndex.intValue) {
            previousTabIndex.intValue = indexTab
        }
        orderViewModel.loadNextItems(indexTab)
    }
}