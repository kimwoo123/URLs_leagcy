package com.keelim.free.util

import com.keelim.data.model.open.LinkSourceContent
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import timber.log.Timber

class OgTagParser @Inject constructor(
    private val call: (LinkSourceContent) -> Unit
) {
    fun getContents(urlToParse: String) {
        JsoupOgTagParser(urlToParse).execute()
    }

    inner class JsoupOgTagParser(var urlToParse: String) : CoroutineScope {
        private val linkSourceContent = LinkSourceContent()
        private val job: Job = Job()
        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main + job

        fun execute() = launch {
            val result = doInBackground()
            call.invoke(result)
        }

        private suspend fun doInBackground(): LinkSourceContent = withContext(Dispatchers.IO) {
            if (!urlToParse.contains("http")) {
                urlToParse = "http://$urlToParse"
            }

            val result = runCatching {
                val response = Jsoup.connect(urlToParse)
                    .ignoreContentType(true)
                    .userAgent("Mozilla")
                    .referrer("http://www.google.com")
                    .timeout(12000)
                    .followRedirects(true)
                    .execute()
                val doc = response.parse()
                val ogTags = doc.select("meta[property^=og:]")
                when {
                    ogTags.size > 0 ->
                        ogTags.forEachIndexed { index, _ ->
                            val tag = ogTags[index]
                            val text = tag.attr("property")
                            when (text) {
                                "og:image" -> {
                                    linkSourceContent.images = (tag.attr("content"))
                                }
                                "og:description" -> {
                                    linkSourceContent.ogDescription = (tag.attr("content"))
                                }
                                "og:url" -> {
                                    linkSourceContent.ogUrl = (tag.attr("content"))
                                }
                                "og:title" -> {
                                    linkSourceContent.ogTitle = (tag.attr("content"))
                                }
                                "og:site_name" -> {
                                    linkSourceContent.ogSiteName = (tag.attr("content"))
                                }
                                "og:type" -> {
                                    linkSourceContent.ogType = (tag.attr("content"))
                                }
                            }
                        }
                }
            }
            when {
                result.isSuccess -> {
                    return@withContext linkSourceContent
                }
                result.isFailure -> {
                    Timber.e("LinkSourceContent is Error")
                    return@withContext LinkSourceContent()
                }
                else -> {
                    return@withContext LinkSourceContent()
                }
            }
        }
    }
}