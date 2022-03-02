package com.keelim.free.di

import com.keelim.free.util.AppCoroutineDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {
    @Singleton
    @Provides
    fun provideDispatchers(): AppCoroutineDispatchers = AppCoroutineDispatchers(
        io = Dispatchers.IO,
        computation = Dispatchers.Default,
        main = Dispatchers.Main
    )
}