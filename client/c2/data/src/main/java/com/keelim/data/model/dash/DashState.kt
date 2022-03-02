package com.keelim.data.model.dash

import com.keelim.data.model.Recommend

sealed class DashState {
    object UnInitialized : DashState()
    object Loading : DashState()
    data class Error(val message: String) : DashState()
    data class Success(
        val data: Dash
    ) : DashState()
    data class Success2(
        val data:List<Recommend>
    ): DashState()
}