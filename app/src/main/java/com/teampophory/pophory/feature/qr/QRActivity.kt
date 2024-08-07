package com.teampophory.pophory.feature.qr

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.teampophory.pophory.R
import com.teampophory.pophory.common.activity.hideLoading
import com.teampophory.pophory.common.activity.showLoading
import com.teampophory.pophory.common.context.stringOf
import com.teampophory.pophory.common.view.dp
import com.teampophory.pophory.common.view.viewBinding
import com.teampophory.pophory.databinding.ActivityQrBinding
import timber.log.Timber

class QRActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityQrBinding::inflate)
    private val viewModel: QRViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lifecycle.addObserver(QRActivityObserver(this))
        observeUIState()
    }

    class QRActivityObserver(private val activity: QRActivity) : DefaultLifecycleObserver {
        override fun onResume(owner: LifecycleOwner) {
            if (activity.viewModel.uiState.value is QRState.Fail) {
                activity.setResult(RESULT_CANCELED)
                activity.finish()
            }
            activity.binding.decorateBarcodeViewQr.resume()
        }

        override fun onPause(owner: LifecycleOwner) {
            activity.binding.decorateBarcodeViewQr.pause()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            ImageDownloader().unregisterReceiver(activity)
        }
    }

    private fun observeUIState() {
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is QRState.Initial -> {
                    initToolbar()
                    checkAndRequestPermissions()
                    setupWebView()
                    setupBarcodeScanner()
                }

                is QRState.Loading -> showLoading()
                is QRState.Success -> {
                    hideLoading()
                    handleSuccessState(state.uri)
                }

                is QRState.Fail -> {
                    hideLoading()
                    handleFailState()
                }
            }
        }
    }

    private fun initToolbar() {
        binding.toolbarQr.txtToolbarTitle.text = stringOf(R.string.qr_toolbar_text)
        binding.toolbarQr.btnBack.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    private fun checkAndRequestPermissions() {
        val permissionsToRequest = getPermissionsToRequest()
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE,
            )
        }
    }

    private fun getPermissionsToRequest(): MutableList<String> {
        return mutableListOf<String>().apply {
            if (isPermissionNotGranted(Manifest.permission.CAMERA)) {
                add(Manifest.permission.CAMERA)
            }
            if (isPermissionNotGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun isPermissionNotGranted(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission,
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun setupWebView() {
        binding.webViewQr.apply {
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
            }
            webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    if (url != null && NOT_SUPPORT_URLS.contains(url)) {
                        viewModel.uiState.value =
                            QRState.Fail(getString(R.string.qr_image_load_fail))
                    }
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    if (!NOT_SUPPORT_URLS.contains(url)) {
                        fetchImageUrlFromWebView()
                    }
                }
            }
        }
    }

    private fun setupBarcodeScanner() {
        configureViewFinder()
        configureStatusText()
        setBarcodeDecodeAction()
    }

    private fun configureViewFinder() {
        val viewFinder = binding.decorateBarcodeViewQr.viewFinder
        viewFinder.setLaserVisibility(false)
    }

    private fun configureStatusText() {
        val statusTextView = binding.decorateBarcodeViewQr.statusView
        statusTextView.setTextAppearance(com.teampophory.pophory.designsystem.R.style.TextAppearance_Pophory_HeadLine03)
        adjustStatusTextPosition()
    }

    private fun adjustStatusTextPosition() {
        val statusTextView = binding.decorateBarcodeViewQr.statusView
        val barcodeView = binding.decorateBarcodeViewQr.barcodeView
        val statusDetailTextView = binding.tvViewFinderTextDetail

        barcodeView.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            val framingRect = barcodeView.framingRect
            framingRect?.let {
                statusTextView.y = (it.bottom + TEXT_MARGIN_TOP_MAIN.dp).toFloat()
                statusDetailTextView.y =
                    statusTextView.y + statusTextView.height + TEXT_MARGIN_TOP_DETAIL.dp
            }
        }
    }

    private fun setBarcodeDecodeAction() {
        binding.decorateBarcodeViewQr.apply {
            decodeSingle { result ->
                viewModel.uiState.value = QRState.Loading
                binding.webViewQr.loadUrl(result.text)
            }
            setStatusText(getString(R.string.qr_view_finder_text))
        }
    }

    private fun fetchImageUrlFromWebView() {
        val jsScript = FIND_IMAGE_LOGIC

        binding.webViewQr.evaluateJavascript(jsScript) { result ->
            if (result == null || "null" == result) {
                viewModel.uiState.value = QRState.Fail(getString(R.string.qr_image_load_fail))
            } else {
                val imageDownloader = ImageDownloader()
                val imageUrl = result.replace("\"", "")
                imageDownloader.downloadImageFromUrl(this@QRActivity, imageUrl) { uri ->
                    viewModel.uiState.value = if (uri != null) {
                        QRState.Success(uri)
                    } else {
                        QRState.Fail(getString(R.string.qr_image_download_fail))
                    }
                }
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

    private fun handleFailState() {
        runCatching {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(binding.webViewQr.url))
            startActivity(browserIntent)
        }.onFailure { e ->
            Timber.e("Failed to open browser with the given URL", e)
        }
    }

    companion object {
        // 예외 처리 사진관
        private const val POZLE_URL = "http://211.110.139.146/PhotoBooth/no_photo.php"

        // 예외 처리 사진관 리스트
        var NOT_SUPPORT_URLS = arrayOf(POZLE_URL)

        // 권한 요청시 어떤 권한에 대한 요청인지 구분하기 위한 코드
        const val PERMISSION_REQUEST_CODE = 1000
        const val TEXT_MARGIN_TOP_MAIN = 23
        const val TEXT_MARGIN_TOP_DETAIL = 150

        const val FIND_IMAGE_LOGIC = """(function() {
        var images = document.querySelectorAll('img');
        var downloadLinks = document.querySelectorAll('a[href*=".jpg"]');
        var buttonImages = document.querySelectorAll('button[src*=".jpg"]');
        var longestImage = null;
        var longestHeight = 0;

        // Find the tallest image from <img> tags
        for (var i = 0; i < images.length; i++) {
            if (images[i].naturalHeight > longestHeight) {
                longestHeight = images[i].naturalHeight;
                longestImage = images[i];
            }
        }

        if (longestImage) {
            return longestImage.src;
        } else if (downloadLinks.length > 0) {
            return downloadLinks[0].href;
        } else if (buttonImages.length > 0) {
            return buttonImages[0].src;
        }

        return null;
        })();"""
    }
}
