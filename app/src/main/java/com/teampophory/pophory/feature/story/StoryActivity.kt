package com.teampophory.pophory.feature.story

import android.os.Bundle
import android.webkit.WebSettings
import androidx.appcompat.app.AppCompatActivity
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {
    private val binding: ActivityStoryBinding by viewBinding(ActivityStoryBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setOnClickListener()
        initWebView()
    }

    private fun initWebView() {
        binding.wvStory.settings.apply {
            javaScriptEnabled = true // 자바스크립트 허용
            javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기 허용
            loadWithOverviewMode = true // html의 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
            cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐쉬 허용

            /**
             * This request has been blocked; the content must be served over HTTPS
             * https 에서 이미지가 표시 안되는 오류를 해결하기 위한 처리
             */
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        binding.wvStory.loadUrl(url)
    }
    private fun setOnClickListener() {
        binding.ivStoryBack.setOnClickListener {
            finish()
        }
    }

    companion object {
        val url = "https://pophoryofficial.wixsite.com/pophory/porit-story"
    }
}
