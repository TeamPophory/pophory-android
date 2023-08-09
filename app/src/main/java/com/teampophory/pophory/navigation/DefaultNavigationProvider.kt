package com.teampophory.pophory.navigation

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.feature.onboarding.OnBoardingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultNavigationProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : NavigationProvider {
    override fun toOnboarding() = OnBoardingActivity.newInstance(context)
    override fun toLicense(): Intent {
        OssLicensesMenuActivity.setActivityTitle("Open Source Licenses")
        return Intent(context, OssLicensesMenuActivity::class.java)
    }
}
