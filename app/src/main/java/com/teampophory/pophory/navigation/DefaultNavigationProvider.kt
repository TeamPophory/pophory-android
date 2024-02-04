package com.teampophory.pophory.navigation

import android.content.Context
import android.content.Intent
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.teampophory.pophory.common.navigation.NavigationProvider
import com.teampophory.pophory.feature.album.list.AlbumListActivity
import com.teampophory.pophory.feature.home.HomeActivity
import com.teampophory.pophory.feature.signup.SignUpActivity
import com.teampophory.pophory.onboarding.OnBoardingActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DefaultNavigationProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) : NavigationProvider {
    override fun toOnboarding() = OnBoardingActivity.newInstance(context)
    override fun toLicense(): Intent {
        OssLicensesMenuActivity.setActivityTitle("Open Source Licenses")
        return Intent(context, OssLicensesMenuActivity::class.java)
    }

    override fun toHome(): Intent {
        return HomeActivity.getIntent(context)
    }

    override fun toAlbumList(albumId: Long): Intent {
        return AlbumListActivity.newInstance(context, albumId)
    }

    override fun toSignUp(): Intent {
        return Intent(context, SignUpActivity::class.java)
    }
}
