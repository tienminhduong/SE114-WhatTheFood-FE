package com.example.se114_whatthefood_fe.view.deviceScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    val listTab: List<String> = listOf("Gan ban", "Ban chay", "Danh gia tot")
    val listRecommendFood: List<Card> = listOf(
        Card(imageLink = "https://m.yodycdn.com/blog/anh-nen-naruto-yody-vn-95.jpg", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f),
        Card(imageLink = "ht", title = "banh mi", rate = 4.5f, distance = 2.0f, time = 30.0f),
        Card(imageLink = "ht", title = "pho", rate = 4.0f, distance = 1.5f, time = 20.0f),
        Card(imageLink = "ht", title = "com tam", rate = 3.5f, distance = 3.0f, time = 25.0f)
    )
    ListFood(listTab = listTab, listFood = listRecommendFood)
}

@Composable
fun ListFood(modifier: Modifier = Modifier, listTab: List<String>, listFood: List<Card>){
    var selectedIndexTab by remember { mutableIntStateOf(0) }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedIndexTab) {
            listTab.forEachIndexed { index, item ->
                Tab(
                    selected = selectedIndexTab == index,
                    onClick = { selectedIndexTab = index },
                    text = { androidx.compose.material3.Text(text = item) }
                )
            }
        }
        // data
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items = listFood, key = {it.id.toString()}){ card ->
                CardView(card = card)
            }
        }
    }
}

@Composable
fun ListRecommendFood(modifier: Modifier = Modifier, listCard: List<Card>) {
    LazyRow(modifier = modifier.fillMaxWidth()){
        items(items = listCard, key = {it.id.toString()}) { card ->
            CardRecommendView(card = card, imageLoader = rememberOptimizedImageLoader())
        }
    }
}