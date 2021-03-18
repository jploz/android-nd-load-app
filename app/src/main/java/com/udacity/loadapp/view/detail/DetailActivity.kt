package com.udacity.loadapp.view.detail

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.udacity.loadapp.databinding.ActivityDetailBinding
import com.udacity.loadapp.util.EXTRA_DETAIL_STATUS
import com.udacity.loadapp.util.EXTRA_DETAIL_TITLE
import com.udacity.loadapp.util.cancelNotifications
import com.udacity.loadapp.view.main.MainActivity

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.contentDetail.viewModel = viewModel
        binding.lifecycleOwner = this

        cancelNotifications()

        viewModel.setFileName(intent.getStringExtra(EXTRA_DETAIL_TITLE))
        viewModel.setStatus(intent.getStringExtra(EXTRA_DETAIL_STATUS))

        binding.contentDetail.buttonDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cancelNotifications() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancelNotifications()
    }
}
