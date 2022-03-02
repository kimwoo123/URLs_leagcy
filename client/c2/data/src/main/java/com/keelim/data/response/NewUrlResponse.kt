package com.keelim.data.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewUrlResponse(
  @Json(name = "folder_name")
  var folderName: String, // folder1
  @Json(name = "_id")
  var id: String, // 6191c74b3d0d48052b3394e8
  @Json(name = "shared")
  var shared: Boolean, // false
  @Json(name = "urls")
  var urls: List<Url>,
  @Json(name = "users")
  var users: List<User>
) {
  @JsonClass(generateAdapter = true)
  data class Url(
    @Json(name = "added_by")
    var addedBy: AddedBy,
    @Json(name = "memos_id")
    var memosId: String, // 6191c7883d0d48052b3394e9
    @Json(name = "tags")
    var tags: List<String>,
    @Json(name = "thumbnail")
    var thumbnail: String?, // https://via.placeholder.com/200.jpg
    @Json(name = "title")
    var title: String?,
    @Json(name = "url")
    var url: String // https://www.naver.com/
  ) {
    @JsonClass(generateAdapter = true)
    data class AddedBy(
      @Json(name = "avatar")
      var avatar: String, // https://lh3.googleusercontent.com/a-/AOh14GgwTT3gntSnZKxSwF5FkCEnSjjU6gnWDdocsZC8=s96-c
      @Json(name = "email")
      var email: String, // kimh00335@gmail.com
      @Json(name = "nickname")
      var nickname: String, // im keel
      @Json(name = "permission")
      var permission: Int // 0
    )
  }

  @JsonClass(generateAdapter = true)
  data class User(
    @Json(name = "avatar")
    var avatar: String, // https://lh3.googleusercontent.com/a-/AOh14GgwTT3gntSnZKxSwF5FkCEnSjjU6gnWDdocsZC8=s96-c
    @Json(name = "email")
    var email: String, // kimh00335@gmail.com
    @Json(name = "nickname")
    var nickname: String, // im keel
    @Json(name = "permission")
    var permission: Int // 2
  )
}