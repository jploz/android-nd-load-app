package com.udacity.loadapp.view.main

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.udacity.loadapp.R
import com.udacity.loadapp.databinding.ActivityMainBinding
import com.udacity.loadapp.receiver.DownloadReceiver
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var action: NotificationCompat.Action

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private val receiver = DownloadReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = setContentView(this, R.layout.activity_main)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.contentMain.viewModel = viewModel
        binding.lifecycleOwner = this

        custom_button.setOnClickListener {
            viewModel.download()
        }

        createNotificationChannel(
            getString(R.string.loadapp_notification_channel_id),
            getString(R.string.loadapp_notification_channel_name)
        )
    }

    override fun onStart() {
        super.onStart()
        // register receiver for ` download complete` event, it will be unregistered in onStop
        // thus, this implementation can only receive `download complete` events while this
        // activity is visible
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(receiver)
    }

    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description =
                getString(R.string.loadapp_notification_channel_description)

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
