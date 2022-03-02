package com.keelim.free.ui.main.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private var _state: MutableStateFlow<NotificationState> = MutableStateFlow(NotificationState.UnInitialized)
    val state: StateFlow<NotificationState> = _state

    fun fetchRelease() = viewModelScope.launch {
        _state.emit(NotificationState.UnInitialized)

        runCatching {
            urlUseCase.getRelease()
        }.onSuccess {
            _state.emit(NotificationState.Success(it))
        }.onFailure {
            _state.emit(NotificationState.Error("에러가 발생했습니다."))
        }
    }
}
