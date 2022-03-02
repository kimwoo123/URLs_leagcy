package com.keelim.data.response.notification


import com.google.gson.annotations.SerializedName

data class NotificationResponse(
    @SerializedName("data1")
    var notificationTitle: String,
    @SerializedName("data2")
    var notificationContent: String,
    @SerializedName("data3")
    var notificationId: Int,
)