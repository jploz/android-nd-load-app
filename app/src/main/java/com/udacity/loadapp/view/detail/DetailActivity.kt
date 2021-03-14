package com.udacity.loadapp.view.detail

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.loadapp.R
import com.udacity.loadapp.databinding.ActivityDetailBinding
import com.udacity.loadapp.util.EXTRA_DETAIL_STATUS
import com.udacity.loadapp.util.EXTRA_DETAIL_TITLE
import com.udacity.loadapp.util.cancelNotifications
import com.udacity.loadapp.view.main.MainActivity
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        setSupportActionBar(toolbar)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        binding.contentDetail.viewModel = viewModel
        binding.lifecycleOwner = this

        cancelNotifications()

        viewModel.setFileName(intent.getStringExtra(EXTRA_DETAIL_TITLE))
        viewModel.setStatus(intent.getStringExtra(EXTRA_DETAIL_STATUS))

        buttonDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cancelNotifications() {
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.cancelNotifications()
    }
}
