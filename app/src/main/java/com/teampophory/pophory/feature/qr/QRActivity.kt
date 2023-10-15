package com.teampophory.pophory.feature.qr

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
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
    private var downloadID: Long = 0
    private val onDownloadComplete = DownloadCompleteReceiver()

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
            if (ContextCompat.checkSelfPermission(this@QRActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                add(Manifest.permission.CAMERA)
            }
            if (ContextCompat.checkSelfPermission(this@QRActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    private fun setupWebView() {
        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                useWideViewPort = true
            }
            setDownloadListener { url, _, _, _, _ -> handleDownloadRequest(url) }
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

    private fun handleDownloadRequest(url: String) {
        val supportedImageFormats = listOf(".jpg", ".jpeg", ".png")

        if (supportedImageFormats.any { url.endsWith(it, ignoreCase = true) }) {
            downloadImageFromUrl(url)
        }
    }

    private fun downloadImageFromUrl(url: String) {
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setMimeType("image/jpeg")
            val cookies = CookieManager.getInstance().getCookie(url)
            addRequestHeader("cookie", cookies)
            addRequestHeader("User-Agent", webView.settings.userAgentString)
            setDescription("Downloading image...")
            setTitle(URLUtil.guessFileName(url, null, "image/jpeg"))
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, null, "image/jpeg")
            )
        }
        val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        downloadID = dm.enqueue(request)
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
                handleDownloadRequest(result.replace("\"", ""))
            }
        }
    }

    inner class DownloadCompleteReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                handleDownloadCompletion()
            }
        }
    }

    private fun handleDownloadCompletion() {
        val query = DownloadManager.Query().setFilterById(downloadID)
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val cursor = manager.query(query)
        if (cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
            if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                val uriString = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                uriString?.let {
                    viewModel.uiState.value = QRState.Success(Uri.parse(it))
                }
            }
        }
        cursor.close()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.uiState.value is QRState.Fail) {
            setResult(RESULT_CANCELED)
            finish()
        }
        binding.decorateBarcodeView.resume()
        registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onPause() {
        super.onPause()
        binding.decorateBarcodeView.pause()
        unregisterReceiver(onDownloadComplete)
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }

    companion object {
        //권한 요청시 어떤 권한에 대한 요청인지 구분하기 위한 코드
        const val PERMISSION_REQUEST_CODE = 1000
    }
}
