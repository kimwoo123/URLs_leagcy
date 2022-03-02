package com.keelim.data.model

sealed class CallResult {
    object Success : CallResult()
    object Error : CallResult()
    object Loading : CallResult()
}
