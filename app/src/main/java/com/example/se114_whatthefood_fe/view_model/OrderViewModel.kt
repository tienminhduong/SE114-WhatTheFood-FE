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
    // don hang da hoan thanh
    var completedOrderList by mutableStateOf(CustomPaginateList<ShippingInfo>())
    private val paginatorCompletedOrder = DefaultPaginator<Int, ShippingInfo>(
        initializeKey = completedOrderList.page,
        onLoadUpdated = { isLoading ->
            completedOrderList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextPage, location ->
            //testRepo.getItems(nextPage, 15)
            orderModel.getCompletedOrders(pageNumber = nextPage) // Giả sử mỗi trang có 15 mục
        },
        getNextKey = { items ->
            completedOrderList.page + 1
        },
        onError = { error ->
            Log.d("Paginator", "onError: ${error.localizedMessage}")
            completedOrderList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            Log.d("Paginator", "onSuccess received ${items.size} items for page $newKey")
            completedOrderList.items.addAll(items)
            Log.d("Paginator", "allOrderList.items new size: ${completedOrderList.items.size}")
            completedOrderList.page = newKey
            completedOrderList.endReached = items.isEmpty()
        }
    )
    // don hang dang giao
    var deliveringOrderList by mutableStateOf(CustomPaginateList<ShippingInfo>())
    private val paginatorDeliveringOrder = DefaultPaginator<Int, ShippingInfo>(
        initializeKey = deliveringOrderList.page,
        onLoadUpdated = { isLoading ->
            deliveringOrderList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextPage, location ->
            //testRepo.getItems(nextPage, 15)
            orderModel.getDeliveringOrders(pageNumber = nextPage) // Giả sử mỗi trang có 15 mục
        },
        getNextKey = { items ->
            deliveringOrderList.page + 1
        },
        onError = { error ->
            Log.d("Paginator", "onError: ${error.localizedMessage}")
            deliveringOrderList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            Log.d("Paginator", "onSuccess received ${items.size} items for page $newKey")
            deliveringOrderList.items.addAll(items)
            Log.d("Paginator", "allOrderList.items new size: ${deliveringOrderList.items.size}")
            deliveringOrderList.page = newKey
            deliveringOrderList.endReached = items.isEmpty()
        }
    )
    // don hang cho xac nhan
    var pendingOrderList by mutableStateOf(CustomPaginateList<ShippingInfo>())
    private val paginatorPendingOrder = DefaultPaginator<Int, ShippingInfo>(
        initializeKey = pendingOrderList.page,
        onLoadUpdated = { isLoading ->
            pendingOrderList.isLoading = isLoading // Cập nhật trạng thái isLoading
        },
        onRequest = { nextPage, location ->
            //testRepo.getItems(nextPage, 15)
            orderModel.getPendingOrders(pageNumber = nextPage) // Giả sử mỗi trang có 15 mục
        },
        getNextKey = { items ->
            pendingOrderList.page + 1
        },
        onError = { error ->
            Log.d("Paginator", "onError: ${error.localizedMessage}")
            pendingOrderList.error = error.localizedMessage // Cập nhật lỗi
        },
        onSuccess = { items, newKey ->
            Log.d("Paginator", "onSuccess received ${items.size} items for page $newKey")
            pendingOrderList.items.addAll(items)
            Log.d("Paginator", "allOrderList.items new size: ${pendingOrderList.items.size}")
            pendingOrderList.page = newKey
            pendingOrderList.endReached = items.isEmpty()
        }
    )
    // lich su don hang
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
                    if (!pendingOrderList.endReached && !pendingOrderList.isLoading) {
                        paginatorPendingOrder.loadNextItems()
                    }
                }
                1->{
                    if (!deliveringOrderList.endReached && !deliveringOrderList.isLoading) {
                        paginatorDeliveringOrder.loadNextItems()
                    }
                }
                2->{
                    if (!completedOrderList.endReached && !completedOrderList.isLoading) {
                        paginatorCompletedOrder.loadNextItems()
                    }
                }
                else ->{
                    if (!allOrderList.endReached && !allOrderList.isLoading) {
                        paginatorAllOrder.loadNextItems()
                    }
                }
                // Add more cases for other tabs if needed
            }
        }
    }

}