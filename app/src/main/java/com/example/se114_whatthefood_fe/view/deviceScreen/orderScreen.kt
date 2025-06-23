package com.example.se114_whatthefood_fe.view.deviceScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import com.example.se114_whatthefood_fe.ui.theme.HeaderTextSize
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view_model.OrderViewModel
import kotlinx.coroutines.launch

@SuppressLint("ViewModelConstructorInComposable")
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
fun Tab1(modifier: Modifier = Modifier){
    Text(text = "hello1", color = White)
}

@Composable
fun Tab2(modifier: Modifier = Modifier){
    Text(text = "hello2", color = White)
}

@Composable
fun Content(indexTab: Int, modifier: Modifier = Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(16.dp)
    ) {
            when (indexTab) {
                0 -> Tab1()
                1 -> Tab2()
                2 -> Tab1()
                3 -> Tab2()
                4 -> Tab1()
            }
    }

}

@Composable
fun SwipeTabs(pagerState: PagerState, modifier: Modifier = Modifier) {
    HorizontalPager(state = pagerState,
        modifier = modifier.fillMaxSize(),
        beyondViewportPageCount = 1) { page ->
        Content(page)
    }
}

@Composable
fun OrderScreen(orderViewModel: OrderViewModel, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(initialPage = 0,
        pageCount = { 5 }) // Assuming there are 5 tabs

    Column(modifier = Modifier.fillMaxSize()
        .background(brush = Brush.verticalGradient(colors = listOf(LightGreen, White))))
    {
        // header
        HeaderOrderScreen()

//        CustomScrollTab(listOf("Đơn đã mua", "Lịch sử", "Đánh giá", "Đang đến", "Giỏ hàng"),
//           pagerState = pagerState)
        ScrollTab(listOf("Đơn đã mua", "Lịch sử", "Đánh giá", "Đang đến", "Giỏ hàng"),
            pagerState = pagerState,
            modifier = Modifier.fillMaxWidth())
        // tab content
        SwipeTabs(pagerState = pagerState)

    }

}

@Composable
fun ScrollTab(tabTitles: List<String>,
              pagerState: PagerState,
              modifier: Modifier = Modifier){
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
            index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    // Handle tab click
                    // You can use pagerState.animateScrollToPage(index) to change the page
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = Bold,
                        color = if (pagerState.currentPage == index) Color.White else Color.White.copy(alpha = 0.6f)
                    )
                }
            )
        }
    }
}

@Composable
fun CustomScrollTab(
    tabTitles: List<String>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = Color.White,
    inactiveColor: Color = Color.White.copy(alpha = 0.6f),
    indicatorColor: Color = Color.White,
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = modifier,
        //edgePadding = 0.dp,
        containerColor = Color.Transparent,
        contentColor = activeColor,
        indicator = { tabPositions ->
            val currentTabPosition = tabPositions.getOrNull(pagerState.currentPage)
            val nextTabPosition = tabPositions.getOrNull(
                (pagerState.currentPage + 1).coerceAtMost(tabTitles.lastIndex)
            )
            val fraction = pagerState.currentPageOffsetFraction

            if (currentTabPosition != null && nextTabPosition != null) {
                val start = lerp(currentTabPosition.left, nextTabPosition.left, fraction)
                val end = lerp(currentTabPosition.right, nextTabPosition.right, fraction)

                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomStart)
                        .offset(x = start)
                        .width(end - start)
                        .height(3.dp)
                        .background(indicatorColor, shape = RoundedCornerShape(2.dp))
                )
            } else if (currentTabPosition != null) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.BottomStart)
                        .offset(x = currentTabPosition.left)
                        .width(currentTabPosition.width)
                        .height(3.dp)
                        .background(indicatorColor, shape = RoundedCornerShape(2.dp))
                )
            }
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
//                    coroutineScope.launch {
//                        pagerState.animateScrollToPage(index)
//                    }
                },
                text = {
                    Text(
                        text = title,
                        fontSize = 13.sp,
                        fontWeight = Bold,
                        color = if (pagerState.currentPage == index) activeColor else inactiveColor
                    )
                }
            )
        }
    }
}

