package com.keelim.data.model

import com.squareup.moshi.Json

data class Recommend(
    var title: String?,
    var url: String,
    var ogImage: String?
)