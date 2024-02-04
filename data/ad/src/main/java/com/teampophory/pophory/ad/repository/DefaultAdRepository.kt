package com.teampophory.pophory.ad.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teampophory.pophory.ad.entity.AdIdentifier
import com.teampophory.pophory.ad.model.toAdIdentifier
import com.teampophory.pophory.ad.service.AdService
import javax.inject.Inject

class DefaultAdRepository @Inject constructor(
    private val adService: AdService,
    private val sharedPreferences: SharedPreferences,
) : AdRepository {
    override suspend fun getAdConstant(os: String, version: String): Result<List<AdIdentifier>> {
        return runCatching { adService.getAdConstant(os, version).map { it.toAdIdentifier() } }
    }

    override suspend fun setAdConstant(adName: String, adId: String) {
        sharedPreferences.edit {
            putString(adName, adId)
        }
    }

    override suspend fun fetchAdConstant(adName: String): AdIdentifier? {
        return sharedPreferences.getString(adName, null)?.let {
            AdIdentifier(adName, it)
        }
    }
}
