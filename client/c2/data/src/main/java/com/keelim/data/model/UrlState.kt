package com.keelim.data.model

sealed class UrlState {
    object UnInitialized : UrlState()
    object Loading : UrlState()
    data class Success1(val data: List<Folder>) : UrlState()
    object Success2: UrlState()
    data class Error(val message: String) : UrlState()
}
