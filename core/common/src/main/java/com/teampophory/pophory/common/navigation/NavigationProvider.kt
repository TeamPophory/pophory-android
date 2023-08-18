package com.teampophory.pophory.common.navigation

import android.content.Intent

interface NavigationProvider {
    fun toOnboarding(): Intent
    fun toLicense(): Intent
    fun toHome(): Intent
    fun toAlbumList(albumId: Long): Intent
}
