package com.keelim.free.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

@InstallIn(SingletonComponent::class)
@Module
object LoggerModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BASIC
    }
}