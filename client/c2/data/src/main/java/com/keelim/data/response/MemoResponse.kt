package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MemoResponse(
  @Json(name = "_id")
  var id: String, // 61927b2f56e2fda34e9b0fbf
  @Json(name = "memos")
  var memos: List<Memo>
) {
  @JsonClass(generateAdapter = true)
  data class Memo(
    @Json(name = "content")
    var content: String, // 얄루야루
    @Json(name = "created_at")
    var createdAt: String, // 2021-11-16T10:15:17
    @Json(name = "highlight")
    var highlight: Any?, // null
    @Json(name = "_id")
    var id: String, // 61930625b4d44e2f45af62d4
    @Json(name = "updated_at")
    var updatedAt: String, // 2021-11-16T10:15:17
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