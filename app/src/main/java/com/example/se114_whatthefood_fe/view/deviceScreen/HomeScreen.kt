package com.example.se114_whatthefood_fe.view.deviceScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.LighterGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.card.Card
import com.example.se114_whatthefood_fe.view.card.CardRecommendView
import com.example.se114_whatthefood_fe.view.card.CardView
import com.example.se114_whatthefood_fe.view.card.rememberOptimizedImageLoader

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    var selectedIndexTab by remember { mutableIntStateOf(0) }
    val tablist = listOf("Gần bạn", "Bán chạy", "Đánh giá tốt")
    Column(Modifier.background(brush = Brush.verticalGradient(colors = listOf(LightGreen, White)))) {

        SearchBar(modifier = Modifier.height(50.dp))
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)
                                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),){
            item {ListRecommendFood(modifier = modifier.fillMaxWidth().wrapContentHeight())}
//            val listTab: List<String> =
            stickyHeader { TabRowCustom(selectedIndexTab = selectedIndexTab, listTab = tablist,
                onTabSelected = { index ->
                    selectedIndexTab = index
                }) }
//            item {TabRowCustom(selectedIndexTab = selectedIndexTab, listTab = tablist,
//                onTabSelected = { index ->
//                    selectedIndexTab = index
//                })}

            val listCard = when (selectedIndexTab) {
                0 -> getDataGanBan()
                1 -> getDataBanChay()
                2 -> getDataDanhGiaTot()
                else -> getDataGanBan()
            }

            items(items = listCard, key = { it.id.toString() }) { card ->
                CardView(card = card)
            }

        }

    }
}

fun getDataGanBan(): List<Card> {
    // This function should return a list of Card objects for the "Gần bạn" tab
    return listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "GanBan",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
}

fun getDataBanChay(): List<Card> {
    // This function should return a list of Card objects for the "Bán chạy" tab
    return listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "BanChay",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
}

fun getDataDanhGiaTot(): List<Card> {
    // This function should return a list of Card objects for the "Đánh giá tốt" tab
    return listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "DanhGiaTot",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
}

@Composable
fun SearchBar(modifier: Modifier = Modifier) {
    val textSearch = remember { mutableStateOf("") }
    Row(modifier = modifier.clip(shape = RoundedCornerShape(10.dp))
        .background(White)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start){
        Icon(imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = LightGreen,
            modifier = Modifier.size(40.dp))
        TextField(value = textSearch.value,
            onValueChange = { textSearch.value = it },
            placeholder = { Text(text = "Tìm kiếm món ăn",
                fontWeight = FontWeight.W800,
                color = LightGreen) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ))
    }
}

@Composable
fun TabRowCustom(modifier: Modifier = Modifier, selectedIndexTab: Int, listTab: List<String>, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedIndexTab,
        modifier = modifier.clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = LighterGreen,
        contentColor = White,
        indicator = { tabPositions ->
            if (selectedIndexTab < tabPositions.size) {
                TabRowDefaults.SecondaryIndicator(Modifier.tabIndicatorOffset(tabPositions[selectedIndexTab]),
                    color = White)
            }
        }) {
        listTab.forEachIndexed { index, item ->
            Tab(
                selected = selectedIndexTab == index,
                onClick = { onTabSelected(index) },
                text = {
                    Text(text = item,
                        fontWeight = FontWeight.W900
                    )
                }
            )
        }
    }
}
@Composable
fun ListFood(modifier: Modifier = Modifier, listTab: List<String>){
    var selectedIndexTab by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.spacedBy (6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        TabRowCustom(selectedIndexTab = selectedIndexTab, listTab = listOf("Gần bạn", "Bán chạy", "Đánh giá tốt"),
            onTabSelected = { index ->
                selectedIndexTab = index
            })
        // data
        when (selectedIndexTab) {
            0 -> DataTabGanBan(modifier = Modifier)
            1 -> DataTabBanChay(modifier = Modifier.fillMaxHeight())
            2 -> DataTabDanhGiaTot(modifier = Modifier.fillMaxHeight())
            else -> DataTabGanBan(modifier = Modifier.fillMaxHeight())
        }
    }
}

@Composable
fun DataTabGanBan(modifier: Modifier){
    // test data
    val listFood: List<Card> = listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "GanBan",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
    LazyColumn(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items(items = listFood, key = {it.id.toString()}){ card ->
            CardView(card = card)
        }
    }
}

@Composable
fun DataTabBanChay(modifier: Modifier){
    // test data
    val listFood: List<Card> = listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "BanChay",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
    LazyColumn(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items(items = listFood, key = {it.id.toString()}){ card ->
            CardView(card = card)
        }
    }
}

@Composable
fun DataTabDanhGiaTot(modifier: Modifier){
    // test data
    val listFood: List<Card> = listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "DanhGiaTot",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
        ,
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
    LazyColumn(modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(6.dp)) {
        items(items = listFood, key = {it.id.toString()}){ card ->
            CardView(card = card)
        }
    }
}

@Composable
fun ListRecommendFood(modifier: Modifier = Modifier) {
    // data test
    val listCard = listOf(
        Card(
            imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg",
            title = "banh mi",
            rate = 4.5f,
            distance = 2.0f,
            time = 30.0f
        ),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(
            imageLink = "ht",
            title = "com tam",
            rate = 3.5f,
            distance = 3.0f,
            time = 25.0f
        ),
        Card(imageLink = "https://media.istockphoto.com/id/1462352351/vi/anh/ph%E1%BB%9F.jpg?s=612x612&w=0&k=20&c=H8CFdkpTEMIHCrtByEkhpW0um8IPmjPVyeHKYpLyoVc=", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(
            imageLink = "ht",
            title = "banh xeo",
            rate = 3.5f,
            distance = 3.0f,
            time = 25.0f
        )
    )

    Column(verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(vertical = 16.dp)) {
        Box(
            modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .background(color = White)
                .height(30.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "What the food recommend for you",
                fontWeight = Bold,
                color = Color.Black,

                )
        }
        // LazyRow to display the list of recommended food cards
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(items = listCard, key = { it.id.toString() }) { card ->
                CardRecommendView(card = card, imageLoader = rememberOptimizedImageLoader())
            }
        }
    }
}