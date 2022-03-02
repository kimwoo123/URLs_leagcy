package com.keelim.data.api

import com.keelim.data.model.auth.User
import com.keelim.data.model.open.Url
import com.keelim.data.request.NewMemoRequest
import com.keelim.data.request.NewUrlRequest
import com.keelim.data.response.FolderResponse
import com.keelim.data.response.MemoResponse
import com.keelim.data.response.MyUrlResponse
import com.keelim.data.response.NewUrlResponse
import com.keelim.data.response.RecommendResponse
import com.keelim.data.response.ReleaseResponse
import com.keelim.data.response.URLResponse
import com.keelim.data.response.notification.NotificationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FreeServices {
    @POST("other1")
    fun share(token: String): Response<List<Url>>

    @POST("other1")
    fun inject(): Response<Any>

    @GET("notifications")
    fun getNotificationList(userId: Int): Response<List<NotificationResponse>>

    @POST("submitUrl")
    fun submitUrl(token: String, url: String): Response<Any>

    @GET("/token/me")
    suspend fun tokenCheck(): Response<User>

    @GET("/folder/me")
    suspend fun allFolder(): Response<List<URLResponse>>

    @GET("/folder/{id}")
    suspend fun getFolder(
        @Path("id") id: String
    ): Response<FolderResponse>

    @GET("/folder/url/me")
    suspend fun folderUrl(): Response<List<MyUrlResponse>>

    @GET("/memo/{id}")
    suspend fun getMemos(
        @Path("id") id: String
    ): Response<MemoResponse>

    @GET("/other")
    suspend fun getRelease(): Response<List<ReleaseResponse>>

    @GET("/recommend?count=10")
    suspend fun getRecommend(): Response<List<RecommendResponse>>

    @POST("/folder/{folder_id}/url")
    suspend fun postNewUrl(
        @Path("folder_id") folderId: String,
        @Body body: NewUrlRequest
    ): Response<NewUrlResponse>

    @POST("/memo/{memos_id}")
    suspend fun postNewMemo(
        @Path("memos_id") folderId: String,
        @Body body: NewMemoRequest
    ): Response<NewUrlResponse>


//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun sue(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun like(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun authenticate(): Response<UserSampleResponse>
//
//  @Headers("Content-Type: application/json")
//  @POST("other/sue/")
//  fun share(): Response<UserSampleResponse>
//
//
//  @POST("invite/")
//  fun invite(
//    @Query("uid") uid: String,
//  ): Response<ResultResponse>
//
//
//
//  @POST
//  fun getCompletedList(): List<Favorite>
//
//  @GET("api/badge/list")
//  suspend fun getAllBadgeList(): Response<BadgeResponse>
//
//  @GET("/api/badgesusers/{userId}")
//  suspend fun getMyBadge(
//    @Path("userId") userId: Int,
//  ): Response<List<BadgeResponse2>>
//
//  @GET("api/notification/select/{userId}")
//  suspend fun getNotificationList(@Path("userId") userId: Int): Response<List<NotificationResponse>>
//
//  @POST("api/user/signup")
//  suspend fun signup(
//    @Body call: SignUpCall,
//  ): Response<AuthResponse>
//
//  @POST("api/user/login")
//  suspend fun login(
//    @Body call: LoginCall,
//  ): Response<AuthResponse>
//
//  @GET("api/challenge/ranking/point")
//  suspend fun getPointRanking(): Response<List<RankingResponse>>
//
//  @GET("api/challenge/ranking/startdate")
//  suspend fun getStartRanking(): Response<List<RankingResponse>>
//
//  @GET("api/category/list")
//  suspend fun category(): Response<List<CategoryResponse>>
//
//  @DELETE("/api/notification/delete/{notificationId}")
//  suspend fun deleteNoti(@Path("notificationId") notificationId: Int): Response<NotificationDeleteResponse>
//
//  @GET("api/challenge/list")
//  suspend fun challengeList(): Response<List<ChallengeResponse>>
//
//
//  @GET("api/challenge/search/title/{searchTitle}")
//  suspend fun search(
//    @Path(value = "searchTitle",
//      encoded = true) query: String,
//  ): Response<List<SearchResponse>>
//
//  @POST("api/challenge/register/new-challenge")
//  suspend fun createChallengeList(
//    @Body call: ChallengeCall,
//  ): Response<ResultResponse>
//
//
//  @GET("api/user/userinfo/{userId}")
//  suspend fun profile(@Path("userId") userId: Int): Response<ProfileResponse>
//
//  @GET("api/challenge/{challengeId}")
//  suspend fun detail(@Path("challengeId") uid: Int): Response<ChallengeResponse>
//
//  @GET("api/challenge/search/user/{userId}")
//  suspend fun getIngList(@Path("userId") userId: Int): Response<List<SearchResponse>>
//
//
//  @GET("api/challenge/opponent/{challengeId}")
//  suspend fun getOpponent(@Path("challengeId") uid: Int): Response<ChallengeResponse>
//
//  @GET("api/article/challenge/{challengeId}")
//  suspend fun article(@Path("challengeId") challenge: Int): Response<List<ArticleResponse>>
//
//
//  @GET("api/comment/article/{articleId}")
//  suspend fun comments(@Path("articleId") article: Int): Response<List<CommentResponse>>
//
//  @POST("api/challenge/register/new-user")
//  suspend fun sign(
//    @Body call: SignCall,
//  ): Response<ResultResponse>
//
//  @POST("api/comment/create")
//  suspend fun writeComment(
//    @Body call: WriteComment,
//  )
//
//
//  @POST("api/article/create")
//  suspend fun upload(
//    @Body call: CreateCall,
//  ): Response<ArticleCreateResponse>
//
//  @GET("api/image/get/article/{articleId}")
//  suspend fun image(@Path("articleId") article: Int): Response<List<String>>
//
//  @Multipart
//  @POST("api/image/save/article")
//  suspend fun imageUpload(
//    @Part articleId: MultipartBody.Part,
//    @Part file: MultipartBody.Part?,
//  )
}
