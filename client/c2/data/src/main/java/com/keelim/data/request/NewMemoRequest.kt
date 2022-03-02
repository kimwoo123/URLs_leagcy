package com.keelim.data.request

import com.squareup.moshi.Json

data class NewMemoRequest(
    @Json(name = "highlight") val highlight: String,
    @Json(name = "content") val content: String,
)
