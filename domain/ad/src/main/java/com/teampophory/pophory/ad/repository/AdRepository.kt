package com.teampophory.pophory.ad.repository

import com.teampophory.pophory.ad.entity.AdConstant

interface AdRepository {
    suspend fun getAdConstant(os: String, version: String): Result<List<AdConstant>>

    suspend fun setAdConstant(adName: String, adId: String)

    suspend fun fetchAdConstant(adName: String): AdConstant?
}
