package com.example.se114_whatthefood_fe.view.deviceScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.se114_whatthefood_fe.ui.theme.LightGreen
import com.example.se114_whatthefood_fe.ui.theme.White
import com.example.se114_whatthefood_fe.view.card.SearchResultCardView
import com.example.se114_whatthefood_fe.view_model.FoodViewModel

@Composable
fun searchScreen(
    navController: NavController,
    foodViewModel: FoodViewModel,
    modifier: Modifier = Modifier
) {

    val results = foodViewModel._searchResults.value

    Column(modifier = Modifier.padding(10.dp)) {
        SearchBarInSearchScreen(
            onSearch = { keyword ->
                Log.d("Search", "Searching for: $keyword")
                foodViewModel.searchFood(keyword = keyword)
            },
            modifier = Modifier.wrapContentHeight()
        )

        Spacer(modifier = Modifier.padding(8.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(results) { item ->
                SearchResultCardView(
                    card = item,
                    onClick = {
                        navController.navigate("DetailFoodItem/${item.id}")
                    }
                )
            }
        }
    }
}

@Composable
fun SearchBarInSearchScreen(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val textSearch = remember { mutableStateOf("") }
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(White)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            tint = LightGreen,
            modifier = Modifier
                .size(40.dp)
                .padding(start = 10.dp)
        )
        TextField(
            value = textSearch.value,
            onValueChange = { textSearch.value = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm món ăn",
                    fontWeight = FontWeight.Bold,
                    color = LightGreen
                )
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.weight(1f),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(textSearch.value)
                }
            )
        )
    }
}
