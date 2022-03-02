package com.keelim.free.ui.main.dash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.dash.Dash
import com.keelim.data.model.dash.DashState
import com.keelim.data.model.open.Url
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@HiltViewModel
class DashViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<DashState> = MutableStateFlow(DashState.UnInitialized)
    val state: StateFlow<DashState> = _state

    init {
        init()
        recommed()
    }

    fun init() = viewModelScope.launch {
        _state.emit(DashState.Loading)
        runCatching {
            val folders = urlUseCase.myFolder()
            val urls = ArrayList<Url>()
            folders.forEach {
                val resultUrls = urlUseCase.getFolder(it.folder_id)
                urls += resultUrls
            }
            Pair(urls.size, folders.size)
        }.onSuccess {
            _state.emit(
                DashState.Success(Dash(
                    it.first,
                    it.second
                ))
            )
        }.onFailure {
            _state.emit(DashState.Error("에러가 발생하였습니다."))
        }
    }

    fun recommed() = viewModelScope.launch {
        runCatching {
            urlUseCase.getRecommend()
        }.onSuccess {
            Timber.d("데아터를 가지고 있난 ${it}")
            _state.emit(
                DashState.Success2(it)
            )
        }.onFailure {
            _state.emit(DashState.Error("에러가 발생하였습니다."))
        }
    }
}