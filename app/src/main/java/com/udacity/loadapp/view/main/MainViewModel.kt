package com.udacity.loadapp.view.main

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.udacity.loadapp.R

class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    var radioButtonId = MutableLiveData<Int>()

    private var downloadID: Long = 0

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
        private const val CHANNEL_ID = "channelId"
    }

    fun download() {
        Log.i("MainViewModel", "radioButtonId: ${radioButtonId.value}")
        return
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(app.getString(R.string.app_name))
                .setDescription(app.getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager =
            app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }
}
