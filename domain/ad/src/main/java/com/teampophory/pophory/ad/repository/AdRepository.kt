package com.teampophory.pophory.ad.repository

import com.teampophory.pophory.ad.entity.AdConstant

interface AdRepository {
    suspend fun getAdConstant(os: String, version: String): Result<List<AdConstant>>

    suspend fun saveAdConstant(adName: String, adId: String)
}
