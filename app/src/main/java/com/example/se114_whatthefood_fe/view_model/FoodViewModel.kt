package com.example.se114_whatthefood_fe.view_model

import android.content.Context
import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.util.DefaultPaginator
import com.example.se114_whatthefood_fe.util.PaginateList
import com.example.se114_whatthefood_fe.util.TestRepo
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class FoodViewModel(private val foodModel: FoodModel) : ViewModel(){
    // test phan trang
    val testRepo = TestRepo()
    var tabGanBanListTest by mutableStateOf(PaginateList())
    private val paginator = DefaultPaginator<Int, FoodItemResponse>(
        initializeKey = tabGanBanListTest.page,
        onLoadUpdated = { isLoading ->
            tabGanBanListTest = tabGanBanListTest.copy(isLoading = isLoading)
        },
        onRequest = { nextKey ->
            testRepo.getItems(nextKey, 15)
        },
        getNextKey = { items ->
            tabGanBanListTest.page + 1
        },
        onError = { error ->
            tabGanBanListTest = tabGanBanListTest.copy(error = error.localizedMessage)
        },
        onSuccess = { items, newKey ->
            tabGanBanListTest = tabGanBanListTest.copy(
                items = tabGanBanListTest.items + items,
                page = newKey,
                endReached = items.isEmpty()
            )
        }
    )

    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }
    init{
        viewModelScope.launch {
            loadNextItems()
        }
    }


    //
    val tabGanBanList = mutableStateOf<List<FoodItemResponse>>(emptyList())
    val tabBanChayList = mutableStateOf<List<FoodItemResponse>>(emptyList())
    val tabDanhGiaTotList = mutableStateOf<List<FoodItemResponse>>(emptyList())
//    init{
//        viewModelScope.launch {
//            loadTabGanBanList()
//        }
//    }

    suspend fun loadDanhGiaTotList(
        pageNumber: Int = 0,
        pageSize: Int = 30,
        categoryId: Int = -1,
        nameContains: String = "",
        restaurantId: Int = -1,
        isAvailableOnly: Boolean = true,
        priceLowerThan: Int = Int.MAX_VALUE,
        priceHigherThan: Int = 0,
        sortBy: String = ""
    ) {
        tabDanhGiaTotList.value = getFoodItem(
            pageNumber, pageSize, categoryId, nameContains, restaurantId, isAvailableOnly, priceLowerThan, priceHigherThan, sortBy
        )
    }

    suspend fun loadBanChayList(
        pageNumber: Int = 0,
        pageSize: Int = 30,
        categoryId: Int = -1,
        nameContains: String = "",
        restaurantId: Int = -1,
        isAvailableOnly: Boolean = true,
        priceLowerThan: Int = Int.MAX_VALUE,
        priceHigherThan: Int = 0,
        sortBy: String = ""
    ) {
        tabBanChayList.value = getFoodItem(
            pageNumber, pageSize, categoryId, nameContains, restaurantId, isAvailableOnly, priceLowerThan, priceHigherThan, sortBy
        )
    }
    suspend fun loadTabGanBanList(location: Location? = null) {
        if(location == null)
            return
        // call api
    }

    suspend fun getFoodItem(
        pageNumber: Int = 0,
        pageSize: Int = 30,
        categoryId: Int = -1,
        nameContains: String = "",
        restaurantId: Int = -1,
        isAvailableOnly: Boolean = true,
        priceLowerThan: Int = Int.MAX_VALUE,
        priceHigherThan: Int = 0,
        sortBy: String = ""): List<FoodItemResponse>
    {
        val res = foodModel.getFoodItem(pageNumber,
            pageSize,
            categoryId,
            nameContains,
            restaurantId,
            isAvailableOnly,
            priceLowerThan,
            priceHigherThan,
            sortBy
        ).body()
        return res ?: emptyList()
    }

}