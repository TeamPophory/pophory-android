package com.teampophory.pophory.config.ad

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber

class AdmobRewardedAdService @AssistedInject constructor(
    @ActivityContext private val context: Context,
    @Assisted private val adUnitId: String,
) {

    private var admobRewardEvents: AdmobRewardEvents? = null

    fun setAdmobRewardEvents(admobRewardEvents: AdmobRewardEvents) {
        this.admobRewardEvents = admobRewardEvents
    }

    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(
            context,
            adUnitId,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Timber.e(adError.toString())
                    admobRewardEvents?.onAdFailedToLoad(adError)
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    Timber.d("Ad was loaded.")
                    initAdmobRewardListener(ad)
                    show(ad)
                    admobRewardEvents?.onAdLoaded()
                }
            },
        )
    }

    private fun initAdmobRewardListener(ad: RewardedAd) {
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                Timber.d("Ad clicked.")
                admobRewardEvents?.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                Timber.d("Ad dismissed.")
                admobRewardEvents?.onAdDismissed()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                Timber.e(adError.toString())
                admobRewardEvents?.onAdFailedToShow(adError)
            }

            override fun onAdImpression() {
                Timber.d("Ad impression.")
                admobRewardEvents?.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                Timber.d("Ad showed.")
                admobRewardEvents?.onAdShowed()
            }
        }
    }

    private fun show(ad: RewardedAd) {
        val activity = context as? Activity ?: return
        ad.show(activity) { rewardItem ->
            admobRewardEvents?.onUserEarnedReward(rewardItem)
            val rewardAmount = rewardItem.amount
            val rewardType = rewardItem.type
            Timber.d("User earned the reward.")
        }
    }

    interface AdmobRewardEvents {
        fun onAdLoaded() {}
        fun onAdFailedToLoad(error: LoadAdError) {}
        fun onAdClicked() {}
        fun onAdDismissed() {}
        fun onAdFailedToShow(error: AdError) {}
        fun onAdImpression() {}
        fun onAdShowed() {}
        fun onUserEarnedReward(rewardItem: RewardItem) {}
    }
}

@AssistedFactory
interface AdmobRewardedAdFactory {
    fun create(adUnitId: String): AdmobRewardedAdService
}
