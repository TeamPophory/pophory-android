package com.teampophory.pophory.ad.usecase

import com.teampophory.pophory.ad.entity.AdConstant
import com.teampophory.pophory.ad.repository.AdRepository
import javax.inject.Inject

class SaveAdConstantUseCase @Inject constructor(
    private val repository: AdRepository
) {
    suspend operator fun invoke(adName: String, adId: String) {
        repository.saveAdConstant(adName, adId)
    }
}
