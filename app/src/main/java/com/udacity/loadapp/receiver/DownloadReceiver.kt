package com.udacity.loadapp.receiver

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        Log.i("DownloadReceiver", "BroadcastReceiver received id = $id")
    }

//    override fun onReceive(context: Context, intent: Intent) {
//        // Step 1.10 [Optional] remove toast
////        Toast.makeText(context, context.getText(R.string.eggs_ready), Toast.LENGTH_SHORT).show()
//
//        // Step 1.9 add call to sendNotification
//        val notificationManager = ContextCompat.getSystemService(
//            context,
//            NotificationManager::class.java
//        ) as NotificationManager
//
//        notificationManager.sendNotification(
//            context.getText(R.string.eggs_ready).toString(),
//            context
//        )
//    }
}
