package com.udacity.loadapp.receiver

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.util.Log
import androidx.core.content.ContextCompat
import com.udacity.loadapp.util.DownloadRequestsStore
import com.udacity.loadapp.util.sendNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            var message = ""

            val requestsStore = DownloadRequestsStore(context)
            val downloadManager =
                context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val query = DownloadManager.Query()

            val ids = requestsStore.getAll()
            if (ids.contains(id)) {

                requestsStore.remove(id)

                query.setFilterById(id)
                val c: Cursor = downloadManager.query(query)
                if (c.moveToFirst()) {
                    val status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    val title = c.getString(c.getColumnIndex(DownloadManager.COLUMN_TITLE))

                    message = getMessageForStatus(id, status, title)
                }
                c.close()
            }
            if (message != "") {
                sendNotification(context, message)
            }
        }
    }

    private fun getMessageForStatus(
        id: Long,
        status: Int,
        title: String
    ): String {
        var msg = ""

        when (status) {
            DownloadManager.STATUS_SUCCESSFUL -> {
                msg = "$title successfully downloaded."
            }
            DownloadManager.STATUS_FAILED -> {
                msg = "Download of $title failed."
            }
            else -> {
                Log.w(
                    "DownloadReceiver",
                    "unknown status for completed download: id = $id, status = $status"
                )
            }
        }
        return msg
    }

    private fun sendNotification(context: Context, message: String) {
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(message, context)
    }
}
