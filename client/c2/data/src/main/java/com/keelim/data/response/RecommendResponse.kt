package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecommendResponse(
  @Json(name = "og_image")
  var ogImage: String?,
  @Json(name = "title")
  var title: String?,
  @Json(name = "url")
  var url: String
)