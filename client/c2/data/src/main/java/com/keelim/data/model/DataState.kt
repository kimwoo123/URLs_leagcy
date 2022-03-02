package com.keelim.data.model

import com.keelim.data.model.open.Url

sealed class DataState {
    object UnInitialized : DataState()
    object Loading : DataState()
    data class Error(val message: String) : DataState()
    data class Success(val data: List<Url>) : DataState()
}