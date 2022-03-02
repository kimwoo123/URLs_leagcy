package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewMemoResponse(
  @Json(name = "_id")
  var id: String, // 6194e98f9d7d076f55fa5022
  @Json(name = "memos")
  var memos: List<Memo>
) {
  @JsonClass(generateAdapter = true)
  data class Memo(
    @Json(name = "content")
    var content: String, // string
    @Json(name = "created_at")
    var createdAt: String, // 2021-11-17T20:45:29
    @Json(name = "highlight")
    var highlight: String, // string
    @Json(name = "_id")
    var id: String, // 6194eb59f88ab5e9c509c619
    @Json(name = "updated_at")
    var updatedAt: String, // 2021-11-17T20:45:29
    @Json(name = "user")
    var user: User
  ) {
    @JsonClass(generateAdapter = true)
    data class User(
      @Json(name = "avatar")
      var avatar: String, // https://lh3.googleusercontent.com/a-/AOh14GgwTT3gntSnZKxSwF5FkCEnSjjU6gnWDdocsZC8=s96-c
      @Json(name = "email")
      var email: String, // kimh00335@gmail.com
      @Json(name = "nickname")
      var nickname: String // im keel
    )
  }
}