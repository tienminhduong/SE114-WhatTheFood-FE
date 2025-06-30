package com.example.se114_whatthefood_fe.view_model

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.FoodItemResponse
import com.example.se114_whatthefood_fe.model.FoodModel
import com.example.se114_whatthefood_fe.util.DefaultPaginator
import com.example.se114_whatthefood_fe.util.CustomPaginateList
import com.example.se114_whatthefood_fe.util.TestRepo
import kotlinx.coroutines.launch
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response
import kotlin.collections.plus

class FoodViewModel(private val foodModel: FoodModel) : ViewModel(){

    // test phan trang
    val testRepo = TestRepo()
    // tab gan ban
    var location by mutableStateOf<Location?>(null)
    var ganBanList by mutableStateOf(CustomPaginateList<FoodItemNearByResponse>())
    private val paginatorNearBy = DefaultPaginator<Int, FoodItemNearByResponse>(
        initializeKey = ganBanList.page,
        onLoadUpdated = { isLoading ->
            ganBanList.isLoading = isLoading
        },
        onRequest = { nextKey, location ->
            if(location == null) {
                return@DefaultPaginator Response.error(500, "".toResponseBody())
            }
            foodModel.getFoodItemNearBy(location.latitude.toFloat(),
                                        location.longitude.toFloat(),
                nextKey) // Giả sử mỗi trang có 15 mục
        },
        getNextKey = { items ->
            ganBanList.page + 1
        },
        onError = { error ->
            ganBanList.error = error.localizedMessage
        },
        onSuccess = { items, newKey ->
//            ganBanList = ganBanList.copy(items = ganBanList.items + items,
//                page = newKey,
//                endReached = items.isEmpty())
            ganBanList.items.addAll(items)
            ganBanList.page = newKey
            ganBanList.endReached = items.isEmpty()
        },
        getPostion = {
            location // Lấy vị trí nếu có
        }
    )
    // tab danh gia tot
    var goodRateList by mutableStateOf(CustomPaginateList<FoodItemNearByResponse>())
    private val paginatorGoodRate = DefaultPaginator<Int, FoodItemNearByResponse>(
        initializeKey = goodRateList.page,
        onLoadUpdated = { isLoading ->
            goodRateList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextPage, location ->
            //testRepo.getItems(nextPage, 15)
            if(location == null) {
                return@DefaultPaginator Response.error(500, "".toResponseBody())
            }
            foodModel.getFoodItemNearBy(location.latitude.toFloat(),
                location.longitude.toFloat())
        },
        getNextKey = { items ->
            goodRateList.page + 1
        },
        onError = { error ->
            goodRateList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            goodRateList.items.addAll(items)
            goodRateList.page = newKey
            goodRateList.endReached = items.isEmpty()
        }
    )
    // tab ban chay
    var bestSellerList by mutableStateOf(CustomPaginateList<FoodItemNearByResponse>())
    private val paginatorBestSeller = DefaultPaginator<Int, FoodItemNearByResponse>(
        initializeKey = bestSellerList.page,
        onLoadUpdated = { isLoading ->
            bestSellerList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextKey, location ->
            // sua lai keo api
            if(location == null) {
                return@DefaultPaginator Response.error(500, "".toResponseBody())
            }
            foodModel.getFoodItemNearBy(location.latitude.toFloat(),
                location.longitude.toFloat())
        },
        getNextKey = { items ->
            bestSellerList.page + 1
        },
        onError = { error ->
            bestSellerList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            bestSellerList.items.addAll(items)
            bestSellerList.page = newKey
            bestSellerList.endReached = items.isEmpty()
        }
    )
    fun loadNextItems(selectedTab: Int) {
        viewModelScope.launch {
            when(selectedTab) {
                // tab gan ban
                0 -> paginatorNearBy.loadNextItems()
                // tab ban chay
                1 -> paginatorBestSeller.loadNextItems()
                // tab danh gia tot
                2 -> paginatorGoodRate.loadNextItems()
            }
        }
    }
    init{
        viewModelScope.launch {
            loadNextItems(0)
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