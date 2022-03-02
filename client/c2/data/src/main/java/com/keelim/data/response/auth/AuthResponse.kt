package com.keelim.data.response.auth


import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data1")
    var data1: String,
    @SerializedName("data2")
    var data2: Int,
    @SerializedName("data3")
    var data3: Int,
    @SerializedName("message")
    var message: String,
    @SerializedName("response")
    var response: String
)