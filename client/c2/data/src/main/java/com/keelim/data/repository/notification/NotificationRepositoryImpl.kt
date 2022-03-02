package com.keelim.data.repository.notification

import com.keelim.data.api.ApiRequestFactory
import com.keelim.data.model.notification.Notification
import com.keelim.data.model.notification.NotificationType
import com.keelim.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class NotificationRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiRequestFactory: ApiRequestFactory,
) : NotificationRepository {
    override suspend fun getAllNotificationList(userId: Int): List<Notification> =
        withContext(dispatcher) {
            val response = apiRequestFactory.retrofit.getNotificationList(
                userId = userId
            )
            if (response.isSuccessful) {
                response.body()?.mapIndexed { index, notificationResponse ->
                    Notification(
                        notificationResponse.notificationTitle,
                        notificationResponse.notificationContent,
                        NotificationType.NOTIFICATION_BASIC,
                        notificationResponse.notificationId
                    )
                } ?: emptyList()
            } else {
                listOf()
            }
        }
}
