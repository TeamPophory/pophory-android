package com.teampophory.pophory.feature.qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityQrBinding

class QRActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityQrBinding::inflate)
    private val viewModel: QRViewModel by viewModels()
    private lateinit var webView: WebView
    private val imageDownloader = ImageDownloader()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        observeUIState()
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is QRState.Initial -> {
                    binding.toolbarQr.txtToolbarTitle.text = "QR로 등록하기"
                    binding.toolbarQr.btnBack.setOnClickListener { finish() }
                    webView = binding.webView
                    checkPermissions()
                    setupWebView()
                    setupBarcodeScanner()
                }

                is QRState.Loading -> {}
                is QRState.Success -> handleSuccessState(state.uri)
                is QRState.Fail -> handleErrorState()
            }
        }
    }

    private fun handleSuccessState(uri: Uri) {
        val resultIntent = Intent().apply {
            putExtra("downloaded_image_uri", uri.toString())
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }

    private fun handleErrorState() {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webView.url))
        startActivity(browserIntent)
    }

    private fun checkPermissions() {
        val permissionsToRequest = mutableListOf<String>().apply {
            if (ContextCompat.checkSelfPermission(
                    this@QRActivity,
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                add(Manifest.permission.CAMERA)
            }
            if (ContextCompat.checkSelfPermission(
                    this@QRActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun setupWebView() {
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    fetchImageUrlFromWebView()
                }
            }
        }
    }

    private fun setupBarcodeScanner() {
        binding.decorateBarcodeView.decodeSingle { result ->
            viewModel.uiState.value = QRState.Loading
            webView.loadUrl(result.text)
        }
    }

    private fun fetchImageUrlFromWebView() {
        val jsScript = """(function() {
        var imageElement = document.querySelector('img');
        return imageElement ? imageElement.src : null;
    })();"""

        webView.evaluateJavascript(jsScript) { result ->
            if (result == null || "null" == result) {
                viewModel.uiState.value = QRState.Fail("이미지 URL을 찾을 수 없습니다.")
            } else {
                val imageUrl = result.replace("\"", "")
                imageDownloader.downloadImageFromUrl(this@QRActivity, imageUrl) { uri ->
                    if (uri != null) {
                        viewModel.uiState.value = QRState.Success(uri)
                    } else {
                        viewModel.uiState.value = QRState.Fail("이미지 다운로드 실패")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.uiState.value is QRState.Fail) {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.decorateBarcodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.decorateBarcodeView.pause()
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }

    companion object {
        // 권한 요청시 어떤 권한에 대한 요청인지 구분하기 위한 코드
        const val PERMISSION_REQUEST_CODE = 1000
    }
}
