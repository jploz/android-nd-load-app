package com.udacity.loadapp.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udacity.loadapp.R
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
    }

}