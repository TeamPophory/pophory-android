package com.teampophory.pophory.ad.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.teampophory.pophory.ad.entity.AdConstant
import com.teampophory.pophory.ad.model.toAdConstant
import com.teampophory.pophory.ad.service.AdService
import javax.inject.Inject

class DefaultAdRepository @Inject constructor(
    private val adService: AdService,
    private val sharedPreferences: SharedPreferences
) : AdRepository {
    override suspend fun getAdConstant(os: String, version: String): Result<List<AdConstant>> {
        return runCatching { adService.getAdConstant(os, version).map { it.toAdConstant() } }
    }

    override suspend fun setAdConstant(adName: String, adId: String) {
        sharedPreferences.edit {
            putString(adName, adId)
        }
    }

    override suspend fun fetchAdConstant(adName: String): AdConstant? {
        return sharedPreferences.getString(adName, null)?.let {
            AdConstant(adName, it)
        }
    }
}
