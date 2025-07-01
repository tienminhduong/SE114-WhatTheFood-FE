package com.example.se114_whatthefood_fe.view_model

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.se114_whatthefood_fe.data.remote.FoodItemNearByResponse
import com.example.se114_whatthefood_fe.data.remote.ShippingInfo
import com.example.se114_whatthefood_fe.model.OrderModel
import com.example.se114_whatthefood_fe.util.CustomPaginateList
import com.example.se114_whatthefood_fe.util.DefaultPaginator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrderViewModel(private val orderModel: OrderModel) : ViewModel(){
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()


    var allOrderList by mutableStateOf(CustomPaginateList<ShippingInfo>())
    private val paginatorAllOrder = DefaultPaginator<Int, ShippingInfo>(
        initializeKey = allOrderList.page,
        onLoadUpdated = { isLoading ->
            allOrderList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextPage, location ->
            //testRepo.getItems(nextPage, 15)
            orderModel.getAllOrders(pageNumber = nextPage) // Giả sử mỗi trang có 15 mục
        },
        getNextKey = { items ->
            allOrderList.page + 1
        },
        onError = { error ->
            Log.d("Paginator", "onError: ${error.localizedMessage}")
            allOrderList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            Log.d("Paginator", "onSuccess received ${items.size} items for page $newKey")
            allOrderList.items.addAll(items)
            Log.d("Paginator", "allOrderList.items new size: ${allOrderList.items.size}")
            allOrderList.page = newKey
            allOrderList.endReached = items.isEmpty()
        }
    )
    fun setCurrentTab(tabIndex: Int) {
        if(_currentTab.value != tabIndex) {
            // Only update if the new tab index is different from the current one
            _currentTab.value = tabIndex
        }
    }

    fun loadNextItems(selectedTab: Int){
        viewModelScope.launch{
            when(selectedTab) {
                0 -> {
                    if (!allOrderList.endReached && !allOrderList.isLoading) {
                        paginatorAllOrder.loadNextItems()
                    }
                }
                // Add more cases for other tabs if needed
            }
        }
    }

}