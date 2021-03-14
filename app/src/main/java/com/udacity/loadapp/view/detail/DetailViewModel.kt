package com.udacity.loadapp.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DetailViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> = _fileName

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    fun setFileName(value: String?) {
        _fileName.postValue(value)
    }

    fun setStatus(value: String?) {
        _status.postValue(value)
    }
}
