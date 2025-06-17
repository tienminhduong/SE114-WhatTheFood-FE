package com.example.se114_whatthefood_fe.view.deviceScreen

import android.R.id.tabs
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import androidx.compose.runtime.getValue
import kotlinx.coroutines.launch

@Composable
@Preview
fun OrderScreenPreview() {
    val orderViewModel = OrderViewModel()
    OrderScreen(orderViewModel)
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
        IconButton(onClick = { /* TODO: Implement search functionality */ },
            modifier = Modifier.align(Alignment.CenterEnd))
        {
            Icon(imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                modifier = Modifier.size(30.dp),
                tint = White)
        }
    }
}

@Composable
fun TabRoll(orderViewModel: OrderViewModel, onClickTap: (Int) -> Unit, modifier: Modifier = Modifier){
    val tabTitles = listOf("Đơn đã mua", "Lịch sử", "Đánh giá", "Đang đến", "Giỏ hàng")
    val selectedTab by orderViewModel.currentTab.collectAsState()
    ScrollableTabRow(selectedTab,
        edgePadding = 0.dp,
        containerColor = Color.Transparent,
        indicator = { tabList ->
            TabRowDefaults.PrimaryIndicator(
            Modifier.tabIndicatorOffset(tabList[selectedTab]),
            color = White,
            width = tabList[selectedTab].width
        )
        }) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onClickTap(index) },
                text = {
                    Text(
                        title,
                        color = if (selectedTab == index) Color.White else Color.White.copy(alpha = 0.6f),
                        fontSize = 13.sp,
                        fontWeight = Bold
                    )
                }
            )
        }
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
fun Content(orderViewModel: OrderViewModel, modifier: Modifier = Modifier){
    val indexTab by orderViewModel.currentTab.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
        AnimatedContent(
            targetState = indexTab,
            transitionSpec = {
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            },
            label = "TabContentAnimation"
        ) { tabIndex ->
            when (tabIndex) {
                0 -> Tab1()
                1 -> Tab2()
                2 -> Tab1()
                3 -> Tab2()
                4 -> Tab1()
            }
        }
    }

}

@Composable
fun SwipeTabs( orderViewModel: OrderViewModel, pagerState: PagerState, modifier: Modifier = Modifier) {
    orderViewModel.setCurrentTab(pagerState.currentPage)
    HorizontalPager(state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1) { page ->
        Content(orderViewModel)
    }
}

@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun OrderScreen(orderViewModel: OrderViewModel, modifier: Modifier = Modifier) {
    val selectedTabIndex by orderViewModel.currentTab.collectAsState()
    val pagerState = rememberPagerState(initialPage = selectedTabIndex,
        pageCount = { 5 }) // Assuming there are 5 tabs
    Column(modifier = Modifier.fillMaxSize())
    {
        // header
        HeaderOrderScreen()
        // tab roll
        TabRoll(orderViewModel, onClickTap = { orderViewModel.setCurrentTab(it) })
        // tab content
        //Content(selectedTabIndex.intValue)
        SwipeTabs(orderViewModel = orderViewModel, pagerState = pagerState)
//        SwipeTabs(onChangeTab = {selectedTabIndex.intValue = it},
//            initialTab = selectedTabIndex.intValue,
//            listTabs = listOf("Đơn đã mua", "Lịch sử", "Đánh giá", "Đang đến", "Giỏ hàng"),
//            modifier = Modifier.fillMaxSize()
//        )

    }

}