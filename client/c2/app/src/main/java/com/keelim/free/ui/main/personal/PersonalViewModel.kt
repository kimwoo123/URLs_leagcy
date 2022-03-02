package com.keelim.free.ui.main.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.Folder
import com.keelim.data.model.UrlState2
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class PersonalViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<UrlState2> = MutableStateFlow(UrlState2.UnInitialized)
    val state: StateFlow<UrlState2> get() = _state

    private val _folder = MutableStateFlow<List<Folder>>(emptyList())
    val folder: StateFlow<List<Folder>> get() = _folder

    fun init() = viewModelScope.launch {
        runCatching {
            val data = urlUseCase.myFolder()
            Timber.d("data hello $data")
            _folder.emit(data)
            _state.emit(UrlState2.Success(data))
        }
    }

    fun share(token: String) = viewModelScope.launch {
        _state.emit(UrlState2.Loading)
        val result = kotlin.runCatching { urlUseCase.share(token) }

        when {
            result.isSuccess -> {
                _state.emit(UrlState2.Success(emptyList()))
            }

            result.isFailure -> {
                _state.emit(UrlState2.Error("에러를 표시해줍니다."))
            }

            else -> {
                _state.emit(UrlState2.Error("에러를 표시해줍니다."))
            }
        }
    }
}