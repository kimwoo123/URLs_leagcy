package com.keelim.data.model.open

sealed class OpenState {
    object UnInitialized : OpenState()
    object Loading : OpenState()
    data class Success(val data: LinkSourceContent) : OpenState()
    data class Error(val message: String) : OpenState()
}
