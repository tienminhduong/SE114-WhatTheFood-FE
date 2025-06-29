package com.example.se114_whatthefood_fe.view.deviceScreen

import android.R.attr.fontWeight
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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.model.LocationManager
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.card.Card
import com.example.se114_whatthefood_fe.view.card.CardRecommendView
import com.example.se114_whatthefood_fe.view.card.CardView
import com.example.se114_whatthefood_fe.view.card.rememberOptimizedImageLoader
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

@Composable
@Preview
fun HomeScreenPreview() {
    //HomeScreen()
}

@Composable
fun HomeScreen(foodViewModel: FoodViewModel, modifier: Modifier = Modifier) {
    var selectedIndexTab by remember { mutableIntStateOf(0) }
    val tablist = listOf("Gần bạn", "Bán chạy", "Đánh giá tốt")
    // dung de lay vi tri hien tai cua nguoi dung
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = remember { LocationManager(context, fusedLocationClient)}
    // nhung danh sach de hien
    val ganBanList by foodViewModel.tabGanBanList
    val banChayList by foodViewModel.tabBanChayList
    val danhGiaTotList by foodViewModel.tabDanhGiaTotList

    // fetch du lieu
    FetchData(locationManager = locationManager, foodViewModel = foodViewModel, selectedTab = selectedIndexTab)
    Column(modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp)) {

        SearchBar(modifier = Modifier.wrapContentHeight())
        LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)
                                    .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),){
            item {ListRecommendFood(modifier = modifier.fillMaxWidth().wrapContentHeight())}
            // tab cac lua chon
            stickyHeader { TabRowCustom(selectedIndexTab = selectedIndexTab, listTab = tablist,
                onTabSelected = { index ->
                    selectedIndexTab = index
                }) }
            // list card cua mon an
            val listCard = when (selectedIndexTab) {
                0-> testGetData(foodViewModel = foodViewModel)
//                0 -> getDataGanBan()
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

@Composable
fun FetchData(locationManager: LocationManager, foodViewModel: FoodViewModel, selectedTab: Int){
    LaunchedEffect(selectedTab) {
        when(selectedTab){
            // load list gan ban
            0 -> {
                val location = locationManager.getLocation()
                location?.let {
                    foodViewModel.loadTabGanBanList(location)
                }
            }
            // load list ban chay
            1 -> {
                foodViewModel.loadBanChayList();
            }
            // load list danh gia tot
            2->{
                foodViewModel.loadDanhGiaTotList();
            }

        }
    }
}

fun testGetData(foodViewModel: FoodViewModel): List<Card> {
    // This function should return a list of Card objects for testing purposes
    val listFood = foodViewModel.tabGanBanList
    return listFood.value.map { foodItem ->
        Card(
            id = foodItem.id,
            imageLink = "",
            title = foodItem.foodName,
            rate = 5.0f, // Assuming a default rate for testing
            distance = 10.0f, // Assuming a default distance for testing
            time = 10.0f // Assuming a default time for testing
        )
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
            modifier = Modifier.size(40.dp).padding(start = 10.dp))
        TextField(value = textSearch.value,
            onValueChange = { textSearch.value = it },
            placeholder = { Text(text = "Tìm kiếm món ăn",
                fontWeight = FontWeight.Bold,
                color = LightGreen) },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f))
    }
}

@Composable
fun TabRowCustom(modifier: Modifier = Modifier, selectedIndexTab: Int, listTab: List<String>, onTabSelected: (Int) -> Unit) {
    TabRow(selectedTabIndex = selectedIndexTab,
        modifier = modifier.clip(shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        containerColor = LightGreen,
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

@Composable
fun HomeScreenTest(foodViewModel: FoodViewModel) {
    val ganBanList = foodViewModel.tabGanBanListTest
    Text(text = "Test phan trang",
        modifier = Modifier.padding(8.dp),
        fontWeight = Bold)
    LazyColumn(modifier = Modifier.fillMaxSize()) {

        items(ganBanList.items.size){ i->
            if(i >= ganBanList.items.size -1 && !ganBanList.endReached && !ganBanList.isLoading){
                foodViewModel.loadNextItems()
            }
            val card = ganBanList.items[i]
            Text(text = "${card.foodName + i} - ${card.restaurant.name} - ${card.price} VND",
                modifier = Modifier.padding(8.dp))
        }
        item{
            if(ganBanList.isLoading)
            {
                Row(modifier = Modifier.fillMaxWidth()){
                    CircularProgressIndicator()
                }
            }
        }
    }
}