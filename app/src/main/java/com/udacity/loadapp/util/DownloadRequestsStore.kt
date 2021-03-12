package com.udacity.loadapp.util

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DownloadRequestsStore(context: Context) {

    companion object {
        const val PREFERENCES_NAME = "com.udacity.loadapp.downloads"
        const val KEY_DOWNLOAD_IDS = "DOWNLOADS"
    }

    private var prefs = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    private suspend fun store(ids: List<Long>) {
        withContext(Dispatchers.IO) {
            val encoded = Json.encodeToString(ids)
            Log.i("DownloadRequestStore", "store: ids = '$ids'")
            Log.i("DownloadRequestStore", "store: encoded = '$encoded'")
            prefs.edit().putString(KEY_DOWNLOAD_IDS, encoded).apply()
        }
    }

    suspend fun add(id: Long) {
        withContext(Dispatchers.IO) {
            val ids = getAll()
            ids.add(id)
            store(ids)
        }
    }

    suspend fun remove(id: Long) {
        withContext(Dispatchers.IO) {
            val ids = getAll()
            ids.remove(id)
            store(ids)
        }
    }

    suspend fun getAll(): MutableList<Long> {
        val ids = mutableListOf<Long>()
        withContext(Dispatchers.IO) {
            val encoded = prefs.getString(KEY_DOWNLOAD_IDS, "[]")
            Log.i("DownloadRequestStore", "getAll: encoded = '$encoded'")
            encoded?.let { ids.addAll(Json.decodeFromString<List<Long>>(it)) }
            Log.i("DownloadRequestStore", "getAll: ids = '$ids'")
        }
        return ids
    }
}
