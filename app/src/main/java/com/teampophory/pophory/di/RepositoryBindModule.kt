package com.teampophory.pophory.di

import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.data.repository.DefaultPhotoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryBindModule {

    @Binds
    @Singleton
    fun bindPhotoRepository(
        defaultPhotoRepository: DefaultPhotoRepository
    ): PhotoRepository
}