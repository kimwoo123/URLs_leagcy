package com.keelim.data.model

sealed class UrlState2 {
    object UnInitialized : UrlState2()
    object Loading : UrlState2()
    data class Success(val data: List<Folder>) : UrlState2()
    data class Error(val message: String) : UrlState2()
}
