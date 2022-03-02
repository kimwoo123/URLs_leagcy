package com.keelim.data.model.auth


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "avatar")
    var avatar: String, // https://via.placeholder.com/200.jpg
    @Json(name = "email")
    var email: String, // ssafy@ssafy.com
    @Json(name = "nickname")
    var nickname: String // ssafy
)