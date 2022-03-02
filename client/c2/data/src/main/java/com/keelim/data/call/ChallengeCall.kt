package com.keelim.data.call

import com.google.gson.annotations.SerializedName

data class ChallengeCall(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("nickname")
    val nickname: String,
)
