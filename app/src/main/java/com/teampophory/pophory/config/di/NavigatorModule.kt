package com.teampophory.pophory.config.di

import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.navigation.DefaultNavigationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {
    @Binds
    @Singleton
    fun bindNavigator(navigator: DefaultNavigationProvider): NavigationProvider
}
