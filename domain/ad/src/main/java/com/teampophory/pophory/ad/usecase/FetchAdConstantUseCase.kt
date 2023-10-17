package com.teampophory.pophory.ad.usecase

import com.teampophory.pophory.ad.entity.AdIdentifier
import com.teampophory.pophory.ad.repository.AdRepository
import javax.inject.Inject

class FetchAdConstantUseCase @Inject constructor(
    private val repository: AdRepository
){
    suspend operator fun invoke(adName: String): AdIdentifier? {
        return repository.fetchAdConstant(adName)
    }
}
