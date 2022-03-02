package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReleaseResponse(
    @Json(name = "date")
    var date: String, // 2021-11-04
    @Json(name = "description")
    var description: String, // 조금 더 향상된 검색을 할 수 있습니다.
    @Json(name = "title")
    var title: String, // 정식으로 엘라스틱 서치를 붙였습니다.
    @Json(name = "version")
    var version: String // v0.0.1
)