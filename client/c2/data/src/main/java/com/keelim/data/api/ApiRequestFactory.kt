package com.keelim.data.api

import android.icu.util.TimeUnit
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.mocklets.pluto.PlutoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create


class ApiRequestFactory @Inject constructor(
    private val tokenUtil: TokenUtil
) {
    private val baseUrl = "http://k5b201.p.ssafy.io:4000"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: FreeServices = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor(
                    PlutoInterceptor()
                )
                .addInterceptor(AuthInterceptor())
                .build()
        )
        .build()
        .create()

    class AuthInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val result = runCatching {
                val user = FirebaseAuth.getInstance().currentUser ?: return chain.proceed(request)
                val task: Task<GetTokenResult> = user.getIdToken(false)
                val tokenResult: GetTokenResult =
                    Tasks.await(task, 10, java.util.concurrent.TimeUnit.SECONDS) // Timeout 10 Seconds
                tokenResult.token ?: return chain.proceed(request)
            }

            return if(result.isSuccess){
                val token = result.getOrNull()
                chain.proceed(
                    request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Bearer $token")
                        .build())
            } else{
                chain.proceed(request) // No has auth header
            }
        }
    }
}
