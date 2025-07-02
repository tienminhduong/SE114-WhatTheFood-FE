package com.example.se114_whatthefood_fe.view.deviceScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.Rating
import com.example.se114_whatthefood_fe.model.LocationManager
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.util.CustomPaginateList
import com.example.se114_whatthefood_fe.view.card.BestSellerCardView
import com.example.se114_whatthefood_fe.view.card.Card
import com.example.se114_whatthefood_fe.view.card.CardRecommendView
import com.example.se114_whatthefood_fe.view.card.GoodRateCardView
import com.example.se114_whatthefood_fe.view.card.NearByCardView
import com.example.se114_whatthefood_fe.view.card.rememberOptimizedImageLoader
import com.example.se114_whatthefood_fe.view_model.FoodViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices

@Composable
@Preview
fun HomeScreenPreview() {
    //HomeScreen()
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(foodViewModel: FoodViewModel, modifier: Modifier = Modifier) {
    var selectedIndexTab by remember { mutableIntStateOf(0) }
    val tablist = listOf("Gần bạn", "Bán chạy", "Đánh giá tốt")
    // dung de lay vi tri hien tai cua nguoi dung
    val context = LocalContext.current
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val locationManager = remember { LocationManager(context, fusedLocationClient)}
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    // nhung danh sach de hien
    val ganBanList = foodViewModel.ganBanList
    val banChayList = foodViewModel.bestSellerList
    val danhGiaTotList = foodViewModel.goodRateList
    LaunchedEffect(Unit) {
        if(!locationPermissions.allPermissionsGranted || locationPermissions.shouldShowRationale)
        {
            locationPermissions.launchMultiplePermissionRequest()
        }
        //foodViewModel.location = locationManager.getLocation()
        //Log.i("HomeScreen", "Location: ${foodViewModel.location?.latitude} ${foodViewModel.location?.longitude}")
    }

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        foodViewModel.location = locationManager.getLocation()
//        foodViewModel.location = Location("dummy_provider").apply {
//            latitude = 10.8843273 // Example latitude
//            longitude = 106.7835597 // Example longitude
//        }
        Log.i("homescreenfunct", "Location: ${foodViewModel.location?.latitude} ${foodViewModel.location?.longitude}")
    }
    LaunchedEffect(foodViewModel.location) {
        foodViewModel.loadNextItems(0)
    }
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
//                0 -> getDataGanBan()
                0 -> ganBanList
                1 -> banChayList
                2 -> danhGiaTotList
                else -> CustomPaginateList()
            }
            itemsIndexed(listCard.items, key = { index, _ -> index }) { index, item ->
                if(index >= listCard.items.size -1 && !listCard.endReached && !listCard.isLoading){
                    FetchData(locationManager, foodViewModel, selectedTab = selectedIndexTab)
                }
                when(selectedIndexTab)
                {
                    0-> NearByCardView(card = listCard.items[index])
                    1-> BestSellerCardView(card = listCard.items[index])
                    2-> GoodRateCardView(card = listCard.items[index])
                }
            }
            item{
                if(listCard.isLoading)
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
}

@Composable
fun FetchData(locationManager: LocationManager, foodViewModel: FoodViewModel, selectedTab: Int){
    var previousTabIndex = remember { mutableIntStateOf(-1) }
    LaunchedEffect(selectedTab) {
        // neu la gan ban thi lay vi tri truoc
        if(selectedTab == 0 && (previousTabIndex.intValue != selectedTab))
        {
            foodViewModel.location = locationManager.getLocation()
            Log.i("fetchdatafunct", "Location: ${foodViewModel.location?.latitude} ${foodViewModel.location?.longitude}")
        }
        if(selectedTab != previousTabIndex.intValue) {
            previousTabIndex.intValue = selectedTab
        }
        foodViewModel.loadNextItems(selectedTab)
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
fun ListRecommendFood(modifier: Modifier = Modifier) {
    // data test

    val listCard = listOf(
        FoodItemNearByResponse(
            foodId = 1,
            name = "Bánh mì thịt nướng",
            rating = Rating(average = 4.5f, number = 100),
            distanceInKm = 1.2f,
            distanceInTime = 10,
            imgUrl = "https://example.com/banhmi.jpg",
            soldAmount = 50,
            restaurantName = ""
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
            items(items = listCard, key = { it.foodId }) { card ->
                //CardRecommendView(card = card, imageLoader = rememberOptimizedImageLoader())
            }
        }
    }
}
