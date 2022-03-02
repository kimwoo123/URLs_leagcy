package com.keelim.free.ui.main.dash.more

import com.keelim.data.model.dash.More
import com.keelim.data.model.open.Url


sealed class MoreState {
    object UnInitialized : MoreState()
    object Loading : MoreState()
    data class Error(val message: String) : MoreState()
    data class Success1(
        val data: List<Url>
    ) : MoreState()

    data class Success2(
        val data: List<More>
    ) : MoreState()
}
