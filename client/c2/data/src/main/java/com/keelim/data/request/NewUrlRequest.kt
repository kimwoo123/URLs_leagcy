package com.keelim.data.request

import com.squareup.moshi.Json

data class NewUrlRequest(
    @Json(name = "url") val url: String,
    @Json(name = "tags") val tags: List<String>
)
