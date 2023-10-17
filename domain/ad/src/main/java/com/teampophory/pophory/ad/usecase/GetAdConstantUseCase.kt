package com.teampophory.pophory.ad.usecase

import com.teampophory.pophory.ad.entity.AdIdentifier
import com.teampophory.pophory.ad.repository.AdRepository
import javax.inject.Inject


class GetAdConstantUseCase @Inject constructor(
    private val repository: AdRepository
) {
    suspend operator fun invoke(os: String, version: String): Result<List<AdIdentifier>> {
        return repository.getAdConstant(os, version)
    }
}
