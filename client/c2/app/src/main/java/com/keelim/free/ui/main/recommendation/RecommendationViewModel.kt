package com.keelim.free.ui.main.recommendation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.data.model.MainState
import com.keelim.data.model.Recommend
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecommendationViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<MainState> = MutableStateFlow(MainState.UnInitialized)
    val state: StateFlow<MainState> = _state

    private val _basic: MutableLiveData<List<Recommend>> = MutableLiveData(emptyList())
    val basic: LiveData<List<Recommend>> = _basic

    fun init() = viewModelScope.launch {
        _state.emit(MainState.UnInitialized)
        runCatching {
            urlUseCase.getRecommend()
        }.onSuccess {
            _state.emit(MainState.Success(it))
        }.onFailure {
            _state.emit(MainState.Error("데이터 로드 중 오류가 발생했습니다."))
        }
    }

    fun setData(data: List<Recommend>) {
        _basic.value = data
    }
}
