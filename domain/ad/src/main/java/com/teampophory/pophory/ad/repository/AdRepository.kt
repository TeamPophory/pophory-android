package com.teampophory.pophory.ad.repository

import com.teampophory.pophory.ad.entity.AdIdentifier

interface AdRepository {
    suspend fun getAdConstant(os: String, version: String): Result<List<AdIdentifier>>

    suspend fun setAdConstant(adName: String, adId: String)

    suspend fun fetchAdConstant(adName: String): AdIdentifier?
}
