package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class URLResponse(
    @Json(name = "folder_id")
    var folderId: String, // 618a8cd5645a40df0ec69ac6
    @Json(name = "folder_name")
    var folderName: String, // folder2
    @Json(name = "shared")
    var shared: Boolean // false
)