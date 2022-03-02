package com.keelim.domain.di

import com.keelim.data.repository.notification.NotificationRepository
import com.keelim.data.repository.url.UrlRepository
import com.keelim.domain.domain.NotificationUseCase
import com.keelim.domain.domain.url.UrlUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideNotificationUseCase(
        notificationRepository: NotificationRepository,
    ): NotificationUseCase {
        return NotificationUseCase(
            notificationRepository
        )
    }

    @Provides
    @ViewModelScoped
    fun provideUrlUseCase(
        urlRepository: UrlRepository,
    ): UrlUseCase {
        return UrlUseCase(
            urlRepository
        )
    }
}
