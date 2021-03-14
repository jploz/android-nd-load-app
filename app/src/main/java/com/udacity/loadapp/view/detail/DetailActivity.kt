package com.udacity.loadapp.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.udacity.loadapp.R
import com.udacity.loadapp.databinding.ActivityDetailBinding
import kotlinx.android.synthetic.main.activity_detail.*

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

    }
}
