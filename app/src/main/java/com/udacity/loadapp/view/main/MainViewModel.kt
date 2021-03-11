package com.udacity.loadapp.view.main

import android.app.Application
import android.app.DownloadManager
import android.app.NotificationManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.loadapp.R
import com.udacity.loadapp.util.sendNotification

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    var radioButtonId = MutableLiveData<Int>()

    private var downloadID: Long = 0

    fun download() {
        val url: String
        when (radioButtonId.value) {

            R.id.radioButtonGlide -> {
                url = app.getString(R.string.url_glide)
            }

            R.id.radioButtonLoadApp -> {
                url = app.getString(R.string.url_load_app)
            }

            R.id.radioButtonRetrofit -> {
                url = app.getString(R.string.url_retrofit)
            }

            else -> {
                url = ""
            }
        }

        Log.i("MainViewModel", "download: URL = '$url'")

        if (url != "") {
            enqueueDownload(url)
            toastDownloadStarted()
        } else {
            toastNoDownloadSelected()
        }
    }

    private fun enqueueDownload(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(app.getString(R.string.app_name))
                .setDescription(app.getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager =
            app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        downloadID = downloadManager.enqueue(request)
    }

    private fun toastNoDownloadSelected() {
        Toast.makeText(app, app.getString(R.string.no_download_selected), Toast.LENGTH_LONG).show()
    }

    private fun toastDownloadStarted() {
        Toast.makeText(app, "Download was started in background.", Toast.LENGTH_SHORT).show()
    }

    private fun sendNotification(message: String) {
        val notificationManager = ContextCompat.getSystemService(
            app,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(message, app)
    }
}
