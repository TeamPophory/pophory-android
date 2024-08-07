package com.teampophory.pophory.feature.qr

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Environment
import android.webkit.CookieManager
import android.webkit.URLUtil
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService

class ImageDownloader {
    private var receiver: BroadcastReceiver? = null

    fun downloadImageFromUrl(context: Context, url: String, callback: (Uri?) -> Unit) {
        val request = createDownloadRequest(url)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)
        setupDownloadCompletionReceiver(context, downloadID, callback)
    }

    private fun createDownloadRequest(url: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(url)).apply {
            setMimeType("image/jpeg")
            val cookies = CookieManager.getInstance().getCookie(url)
            addRequestHeader("cookie", cookies)
            setDescription("Downloading image...")
            setTitle(URLUtil.guessFileName(url, null, "image/jpeg"))
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, null, "image/jpeg"),
            )
        }
    }

    private fun setupDownloadCompletionReceiver(
        context: Context,
        downloadID: Long,
        callback: (Uri?) -> Unit,
    ) {
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadID == id) {
                    val downloadManager = context?.getSystemService<DownloadManager>() ?: return
                    val query = DownloadManager.Query().setFilterById(downloadID)
                    downloadManager.query(query).use { cursor ->
                        if (cursor.moveToFirst()) {
                            val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                            if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                                val index = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)
                                if (index < 0) {
                                    return
                                }
                                val uriString = cursor.getString(index)
                                uriString?.let {
                                    callback(Uri.parse(it))
                                    return
                                }
                            }
                        }
                    }
                    callback(null)
                }
            }
        }
        ContextCompat.registerReceiver(
            context, receiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    fun unregisterReceiver(context: Context) {
        receiver?.let {
            context.unregisterReceiver(it)
        }
    }
}
