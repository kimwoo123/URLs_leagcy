package com.keelim.free.ui.main.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.DataState
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<DataState> = MutableStateFlow(DataState.UnInitialized)
    val state: MutableStateFlow<DataState> = _state

    fun init(folder: String) = viewModelScope.launch {
        _state.emit(DataState.Loading)
        runCatching {
            urlUseCase.getFolder(folder)
        }.onSuccess {
            _state.emit(DataState.Success(it))
        }.onFailure {
            _state.emit(DataState.Error("네트워크 에러가 발생하였습니다. "))
        }
    }
}