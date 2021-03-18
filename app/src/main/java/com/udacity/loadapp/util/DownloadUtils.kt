package com.udacity.loadapp.util

import android.app.DownloadManager

const val EXTRA_DETAIL_STATUS = "status"
const val EXTRA_DETAIL_TITLE = "title"

data class DownloadInfo(
    val title: String,
    val status: String,
    val message: String
)

fun getDownloadInfo(
    status: Int,
    title: String
): DownloadInfo {
    val msg = getMessageForStatus(status, title)
    val statusAsString = getStatusAsString(status)
    return DownloadInfo(title = title, status = statusAsString, message = msg)
}

private fun getMessageForStatus(
    status: Int,
    title: String
): String {
    return when (status) {
        DownloadManager.STATUS_SUCCESSFUL -> {
            "$title successfully downloaded."
        }
        DownloadManager.STATUS_FAILED -> {
            "Download of $title failed."
        }
        else -> {
            "Download of $title completed with unknown status."
        }
    }
}

private fun getStatusAsString(
    status: Int
): String {
    return when (status) {
        DownloadManager.STATUS_SUCCESSFUL -> {
            "Successfully downloaded"
        }
        DownloadManager.STATUS_FAILED -> {
            "Download failed"
        }
        else -> {
            "unknown status for completed download (status code = $status)"
        }
    }
}
