package com.teampophory.pophory.navigation

import android.content.Context
import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultNavigationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : NavigationProvider {
    override fun toOnboarding() = OnBoardingActivity.newInstance(context)
}
