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

class ImageDownloader() {
    fun downloadImageFromUrl(context: Context, url: String, callback: (Uri?) -> Unit) {
        val request = createDownloadRequest(context, url)
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)
        setupDownloadCompletionReceiver(context, downloadID, callback)
    }

    private fun createDownloadRequest(context: Context, url: String): DownloadManager.Request {
        return DownloadManager.Request(Uri.parse(url)).apply {
            setMimeType("image/jpeg")
            val cookies = CookieManager.getInstance().getCookie(url)
            addRequestHeader("cookie", cookies)
            setDescription("Downloading image...")
            setTitle(URLUtil.guessFileName(url, null, "image/jpeg"))
            allowScanningByMediaScanner()
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                URLUtil.guessFileName(url, null, "image/jpeg")
            )
        }
    }

    private fun setupDownloadCompletionReceiver(
        context: Context,
        downloadID: Long,
        callback: (Uri?) -> Unit
    ) {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (downloadID == id) {
                    val downloadManager =
                        context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                    val query = DownloadManager.Query().setFilterById(downloadID)
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)
                        if (DownloadManager.STATUS_SUCCESSFUL == cursor.getInt(columnIndex)) {
                            val uriString =
                                cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
                            cursor.close()
                            uriString?.let {
                                callback(Uri.parse(it))
                                return
                            }
                        }
                    }
                    cursor.close()
                    callback(null)
                }
            }
        }

        context.registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}
