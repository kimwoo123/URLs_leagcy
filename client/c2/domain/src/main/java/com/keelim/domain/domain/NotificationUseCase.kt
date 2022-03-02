package com.keelim.domain.domain

import com.keelim.data.model.notification.Notification
import com.keelim.data.repository.notification.NotificationRepository
import javax.inject.Inject

class NotificationUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
) {
    suspend operator fun invoke(userId: Int): List<Notification> {
        return notificationRepository.getAllNotificationList(userId)
    }
}
