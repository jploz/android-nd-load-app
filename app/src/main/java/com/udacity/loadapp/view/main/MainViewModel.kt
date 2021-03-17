package com.udacity.loadapp.view.main

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.loadapp.R
import com.udacity.loadapp.util.DownloadRequestsStore
import kotlinx.coroutines.launch


class MainViewModel(private val app: Application) : AndroidViewModel(app) {

    var radioButtonId = MutableLiveData<Int>()

    private val downloadIds = DownloadRequestsStore(app)

    fun download(): Boolean {
        var downloadStarted = false
        val url: String
        val title: String
        when (radioButtonId.value) {

            R.id.radioButtonGlide -> {
                url = app.getString(R.string.url_glide)
                title = "Glide source code"
            }

            R.id.radioButtonLoadApp -> {
                url = app.getString(R.string.url_load_app)
                title = "Udacity project 3 source code"
            }

            R.id.radioButtonRetrofit -> {
                url = app.getString(R.string.url_retrofit)
                title = "Retrofit source code"
            }

            else -> {
                url = ""
                title = ""
            }
        }

        Log.i("MainViewModel", "download: title = '$title', URL = '$url'")

        if (url != "") {
            enqueueDownload(url, title)
            downloadStarted = true
        } else {
            toastNoDownloadSelected()
        }
        return downloadStarted
    }

    private fun enqueueDownload(url: String, title: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(title)
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager =
            app.getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager

        val downloadId = downloadManager.enqueue(request)

        viewModelScope.launch {
            downloadIds.add(downloadId)
        }
    }

    private fun toastNoDownloadSelected() {
        Toast.makeText(app, app.getString(R.string.no_download_selected), Toast.LENGTH_SHORT).show()
    }
}
