package com.keelim.data.repository.url

import com.keelim.data.api.ApiRequestFactory
import com.keelim.data.model.CallResult
import com.keelim.data.model.Folder
import com.keelim.data.model.Recommend
import com.keelim.data.model.auth.User
import com.keelim.data.model.dash.Dash
import com.keelim.data.model.fold.Memo
import com.keelim.data.model.notification.Release
import com.keelim.data.model.open.Url
import com.keelim.data.request.NewMemoRequest
import com.keelim.data.request.NewUrlRequest
import com.keelim.data.response.MyUrlResponse
import com.keelim.di.IoDispatcher
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

class UrlRepositoryImpl @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val apiRequestFactory: ApiRequestFactory,
) : UrlRepository {
    override suspend fun share(token: String): List<Url> = withContext(dispatcher) {
        val response = apiRequestFactory.retrofit.share(token)
        if (response.isSuccessful) {
            response.body()?.mapIndexed { index, url ->

            }
            return@withContext response.body() ?: emptyList()
        } else {
            return@withContext listOf()
        }
    }

    override suspend fun inject(token: String, url: String): CallResult = withContext(dispatcher) {
        return@withContext CallResult.Success
    }

    override suspend fun allFolder(): List<Folder> = withContext(dispatcher) {
        val response = apiRequestFactory.retrofit.allFolder()
        Timber.d("들어온 값 1 ${response.body()}")
        if (response.isSuccessful) {
            response.body()?.toList()!!.map {
                Folder(
                    it.folderId,
                    it.folderName,
                    it.shared
                )
            }
        } else {
            emptyList()
        }
    }

    override suspend fun newOneFolder(token: String): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun detailFolder(fid: String): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun renameFolder(fid: String, name: String): CallResult =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }

    override suspend fun deleteFolder(fid: String): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun folderPermissionChange(
        fid: String,
        email: String,
        permission: Int
    ): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun folderNewUser(fid: String, email: String, permission: Int): CallResult =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }

    override suspend fun folderDeleteUser(fid: String, email: String, permission: Int): CallResult =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }

    override suspend fun getFolder(folder: String): List<Url> = withContext(dispatcher) {
        try {
            val response = apiRequestFactory.retrofit.getFolder(folder)
            val result = response.body()?.urls!!.map {
                Url(
                    url = it.url,
                    thumbnail = it.thumbnail.orEmpty(),
                    tags = it.tags,
                    memos_id = it.memosId,
                )
            }
            Timber.d("성공한 데이터 $result")
            return@withContext result
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext emptyList()
    }

    override suspend fun folderUrl(): Dash = withContext(dispatcher) {
        try {
            val response = apiRequestFactory.retrofit.folderUrl()
            val result = arrayListOf<MyUrlResponse.Url>()
            response.body()?.forEach { data ->
                result += data.urls
            }
            return@withContext Dash(
                result.size,
                response.body()?.size ?: 0,
            )
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext Dash(
            0,
            0,
        )
    }

    override suspend fun getRecommend(): List<Recommend>  = withContext(dispatcher){
        try {
            val response = apiRequestFactory.retrofit.getRecommend()
            val result = response.body()?.map {
                Recommend(
                    it.title,
                    it.url,
                    it.ogImage,
                )
            }
            Timber.d("성공한 데이터 $result")
            return@withContext result!!
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext emptyList()
    }

    override suspend fun folderNewUrl(
        fid: String,
        url: String,
        thumbnail: String,
        tags: String
    ): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun folderDeleteUrl(
        fid: String,
        url: String,
        thumbnail: String,
        tags: String
    ): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun urlAllMemo(mid: String): List<Memo> = withContext(dispatcher) {
        try {
            val response = apiRequestFactory.retrofit.getMemos(mid)
            val result = response.body()?.memos!!.mapIndexed { index, memo ->
                Memo(
                    index.toString(),
                    memo.content,
                    memo.updatedAt
                )
            }
            Timber.d("성공한 데이터 $result")
            return@withContext result
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext emptyList()
    }

    override suspend fun urlNewMemo(mid: String, highlight: String, content: String): CallResult =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }

    override suspend fun urlChangeMemo(
        mid: String,
        highlight: String,
        content: String
    ): CallResult = withContext(dispatcher) {
        TODO("Not yet implemented")
    }

    override suspend fun urlDeleteMemo(msid: String, mid: String): CallResult =
        withContext(dispatcher) {
            TODO("Not yet implemented")
        }

    override suspend fun submitUrl(token: String, url: String): CallResult =
        withContext(dispatcher) {
            val response = apiRequestFactory.retrofit.submitUrl(token, url)
            return@withContext if (response.isSuccessful) {
                CallResult.Success
            } else {
                CallResult.Error
            }
        }

    override suspend fun tokenCheck(token: String): User = withContext(dispatcher) {
        try {
            val response = apiRequestFactory.retrofit.tokenCheck()
            return@withContext response.body() ?: User("", "", "")
        } catch (e: Exception) {
            Timber.e(e)
        }
        return@withContext User("", "", "")
    }

    override suspend fun getRelease(): List<Release> = withContext(dispatcher) {
        try {
            val response = apiRequestFactory.retrofit.getRelease()
            val result = response.body()?.mapIndexed { index, release ->
                Release(
                    release.date,
                    release.description,
                    release.title,
                    release.version
                )
            }
            Timber.d("성공한 데이터 $result")
            return@withContext result!!
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext emptyList()
    }

    override suspend fun newUrl(folderId: String, url: String, change: List<String>): String =
        withContext(dispatcher) {
            try {

                val response = apiRequestFactory.retrofit.postNewUrl(folderId, NewUrlRequest(
                    url,
                    change
                ))
                Timber.d("제대로 된 응답 ${response.body()}")
                val result = response.body()!!.urls.first().memosId
                return@withContext result
            } catch (e: Exception) {
                Timber.e(e)
            }
            Timber.d("성공하지 않는 데이터")
            return@withContext ""
        }

    override suspend fun newMemo(memoId: String, memo: String): String  = withContext(dispatcher){
        try {
            val response = apiRequestFactory.retrofit.postNewMemo(memoId, NewMemoRequest(
                memo,
                memo
            ))
            val result = response.body()!!.urls.first().memosId
            return@withContext result
        } catch (e: Exception) {
            Timber.e(e)
        }
        Timber.d("성공하지 않는 데이터")
        return@withContext ""
    }
}