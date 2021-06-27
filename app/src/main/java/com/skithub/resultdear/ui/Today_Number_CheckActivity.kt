package com.skithub.resultdear.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.databinding.ActivityTodayNumberCheckBinding

class Today_Number_CheckActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodayNumberCheckBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayNumberCheckBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar1)
        supportActionBar?.setTitle(" আজকের ফলাফল চেক")
    }
}