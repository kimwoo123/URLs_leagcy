package com.keelim.free.ui.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.DataState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<DataState> = MutableStateFlow(DataState.UnInitialized)
    val state: StateFlow<DataState> = _state

    fun search(query: String) = viewModelScope.launch {
        _state.emit(DataState.Loading)
//        runCatching {
//        urlUseCase.search(query)
//        }.onSuccess {
//            _state.emit(DataState.Success(emptyList()))
//        }.onFailure {
//            _state.emit(DataState.Error("네트워크 오류가 발생하였습니다."))
//        }
    }
}