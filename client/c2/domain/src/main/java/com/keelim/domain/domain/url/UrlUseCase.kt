package com.keelim.domain.domain.url

import com.keelim.data.model.CallResult
import com.keelim.data.model.Folder
import com.keelim.data.model.Recommend
import com.keelim.data.model.dash.Dash
import com.keelim.data.model.fold.Memo
import com.keelim.data.model.notification.Release
import com.keelim.data.model.open.Url
import com.keelim.data.repository.url.UrlRepository
import timber.log.Timber

class UrlUseCase(
    private val urlRepository: UrlRepository
) {
    suspend fun share(token: String): List<Url> {
        return urlRepository.share(token)
    }

    suspend fun inject(token: String, url: String): CallResult {
        return urlRepository.inject(token, url)
    }

    suspend fun submitUrl(token: String, url: String): CallResult {
        return urlRepository.submitUrl(token, url)
    }

    suspend fun tokenCheck(token: String): Boolean {
        return urlRepository.tokenCheck(token).email.isNotEmpty()
    }

    suspend fun myFolder(): List<Folder> {
        val result = urlRepository.allFolder()
        Timber.i("들어온 값 $result")
        return result
    }

    suspend fun getFolder(folder: String): List<Url> {
        val result = urlRepository.getFolder(folder)
        Timber.d("getFolder $result")
        return result
    }

    suspend fun folderUrlMe(): Dash {
        val result = urlRepository.folderUrl()
        Timber.d("folderUrlMe $result")
        return result
    }

    suspend fun getRecommend(): List<Recommend>{
        val result = urlRepository.getRecommend()
        Timber.d("getRecommend $result")
        return result
    }

    suspend fun getMemos(urlId:String): List<Memo>{
        val result = urlRepository.urlAllMemo(urlId)
        Timber.d("folderUrlMe $result")
        return result
    }

    suspend fun getRecommended(): List<Url> {
        return emptyList()
    }

    suspend fun getRelease(): List<Release>{
        val result = urlRepository.getRelease()
        Timber.d("folderUrlMe $result")
        return result
    }

    suspend fun newUrl(folderId: String, url: String, change: List<String>): String{
        val result = urlRepository.newUrl(folderId, url, change)
        Timber.d("folderUrlMe $result")
        return result
    }

    suspend fun newMemo(memosId: String, memo: String) {
        urlRepository.newMemo(memosId, memo)
    }
}