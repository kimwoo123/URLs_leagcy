package com.keelim.data.repository.notification

import com.keelim.data.model.notification.Notification

interface NotificationRepository {
    suspend fun getAllNotificationList(userId: Int): List<Notification>
}
