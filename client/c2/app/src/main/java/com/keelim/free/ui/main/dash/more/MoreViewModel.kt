package com.keelim.free.ui.main.dash.more

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MoreViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MoreState> = MutableStateFlow(MoreState.UnInitialized)
    val state: StateFlow<MoreState> get() = _state

    init {
        init()
    }

    private fun init() = viewModelScope.launch {
        _state.emit(MoreState.UnInitialized)
        runCatching {
            urlUseCase.getRecommended()
        }.onSuccess {
            _state.emit(MoreState.Success1(it))
        }.onFailure {
            _state.emit(MoreState.Error("네트워크 에러가 발생했습니다."))
        }
    }
}
