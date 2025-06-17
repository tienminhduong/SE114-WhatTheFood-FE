package com.example.se114_whatthefood_fe.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel : ViewModel(){
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    fun setCurrentTab(tabIndex: Int) {
        if(_currentTab.value != tabIndex) {
            // Only update if the new tab index is different from the current one
            _currentTab.value = tabIndex
        }
    }


}