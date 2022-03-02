package com.keelim.core.notification

interface NotificationBuilder {
    fun showLegacyNotification(list: List<Any>)
    fun showAlarmNotification(list: List<Any>)
}