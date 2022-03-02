package com.keelim.free.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.keelim.domain.domain.url.UrlUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val urlUseCase: UrlUseCase
) : ViewModel() {
    private val _token = MutableStateFlow<Boolean>(false)
    val token: StateFlow<Boolean> = _token

    fun tokenCheck(token: String) = viewModelScope.launch {
        _token.emit(urlUseCase.tokenCheck(token))
    }

    fun setTokenCheck() = viewModelScope.launch {
        _token.emit(true)
    }
}