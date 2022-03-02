package com.keelim.free.ui.main.notification

import com.keelim.data.model.notification.Release


sealed class NotificationState {
    object UnInitialized : NotificationState()
    object Loading : NotificationState()
    data class Error(
        val message: String
    ) : NotificationState()
    data class Success(
        val data: List<Release>
    ) : NotificationState()
}
