package com.keelim.free.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SettingViewModel @Inject constructor(

) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun loading() = viewModelScope.launch {
        delay(1_000L)
        _loading.value = true
        delay(500L)
        _loading.value = false
    }
}