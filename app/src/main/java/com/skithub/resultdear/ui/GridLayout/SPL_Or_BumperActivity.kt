package com.skithub.resultdear.ui.GridLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivitySPLOrBumperBinding

class SPL_Or_BumperActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySPLOrBumperBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySPLOrBumperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle("স্পেশাল ও বাম্পার")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)



    }
}