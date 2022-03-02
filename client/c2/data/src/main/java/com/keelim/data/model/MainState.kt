package com.keelim.data.model

sealed class MainState {
    object UnInitialized : MainState()
    object Loading : MainState()
    data class Success(val data: List<Recommend>) : MainState()
    data class Error(val message: String) : MainState()
}