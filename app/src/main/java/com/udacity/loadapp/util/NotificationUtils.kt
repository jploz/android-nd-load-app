package com.udacity.loadapp.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.udacity.loadapp.R
import com.udacity.loadapp.view.detail.DetailActivity

private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

/**
 * Builds and delivers the notification.
 *
 * @param context, activity context.
 */
fun NotificationManager.sendNotification(applicationContext: Context, downloadInfo: DownloadInfo) {
    // Create the content intent for the notification, which launches the target activity
    val contentIntent = Intent(applicationContext, DetailActivity::class.java)
        .putExtra(EXTRA_DETAIL_STATUS, downloadInfo.status)
        .putExtra(EXTRA_DETAIL_TITLE, downloadInfo.title)

    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val cloudImage = BitmapFactory.decodeResource(
        applicationContext.resources,
        R.drawable.cloud_download
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.loadapp_notification_channel_id)
    )
        .setSmallIcon(R.drawable.ic_cloud_download)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(downloadInfo.message)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setLargeIcon(cloudImage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setOnlyAlertOnce(true)

    notify(NOTIFICATION_ID, builder.build())
}

/**
 * Cancels all notifications.
 *
 */
fun NotificationManager.cancelNotifications() {
    cancelAll()
}
