package com.teampophory.pophory.feature.album.share

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityReceiveSharedImageBinding
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ReceiveSharedImageActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityReceiveSharedImageBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            runCatching {
                Firebase.dynamicLinks.getDynamicLink(intent).await()
            }.onSuccess {
                it.link?.getQueryParameter("u")?.let {
                    // TODO by Nunu uuid 활용해서 서버통신 로직 짜기
                }
            }.onFailure {
                Timber.e(it)
                finish()
            }
        }
    }
}
