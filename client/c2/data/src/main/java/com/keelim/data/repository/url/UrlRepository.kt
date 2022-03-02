package com.keelim.data.repository.url

import com.keelim.data.model.CallResult
import com.keelim.data.model.Folder
import com.keelim.data.model.Recommend
import com.keelim.data.model.auth.User
import com.keelim.data.model.dash.Dash
import com.keelim.data.model.fold.Memo
import com.keelim.data.model.notification.Release
import com.keelim.data.model.open.Url

interface UrlRepository {
    suspend fun share(token: String): List<Url>
    suspend fun inject(token: String, url: String): CallResult
    suspend fun allFolder(): List<Folder>
    suspend fun newOneFolder(token: String): CallResult
    suspend fun detailFolder(fid: String): CallResult
    suspend fun renameFolder(fid: String, name: String): CallResult
    suspend fun deleteFolder(fid: String): CallResult
    suspend fun folderPermissionChange(fid: String, email: String, permission: Int): CallResult
    suspend fun folderNewUser(fid: String, email: String, permission: Int): CallResult
    suspend fun folderDeleteUser(fid: String, email: String, permission: Int): CallResult
    suspend fun getFolder(folder: String): List<Url>
    suspend fun folderUrl(): Dash
    suspend fun getRecommend(): List<Recommend>
    suspend fun folderNewUrl(fid: String, url: String, thumbnail: String, tags: String): CallResult
    suspend fun folderDeleteUrl(
        fid: String,
        url: String,
        thumbnail: String,
        tags: String
    ): CallResult
    suspend fun urlAllMemo(mid: String): List<Memo>
    suspend fun urlNewMemo(mid: String, highlight: String, content: String): CallResult
    suspend fun urlChangeMemo(mid: String, highlight: String, content: String): CallResult
    suspend fun urlDeleteMemo(msid: String, mid: String): CallResult
    suspend fun submitUrl(token: String, url: String): CallResult
    suspend fun tokenCheck(token: String): User
    suspend fun getRelease(): List<Release>
    suspend fun newUrl(folderId: String, url: String, change: List<String>): String
    suspend fun newMemo(memoId:String, memo:String): String
}