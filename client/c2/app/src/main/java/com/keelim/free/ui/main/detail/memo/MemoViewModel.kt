package com.keelim.free.ui.main.detail.memo

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Recomposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.DataState
import com.keelim.data.model.MemoState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MemoViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
): ViewModel() {
    private val _state: MutableStateFlow<MemoState> = MutableStateFlow(MemoState.UnInitialized)
    val state: StateFlow<MemoState> = _state

    fun init(urlId:String) = viewModelScope.launch {
        _state.emit(MemoState.Loading)
        runCatching {
            urlUseCase.getMemos(urlId)
        }.onSuccess {
            _state.emit(MemoState.Success(it))
        }.onFailure {
            _state.emit(MemoState.Error("메모를 불러오지 못했습니다."))
        }
    }
}