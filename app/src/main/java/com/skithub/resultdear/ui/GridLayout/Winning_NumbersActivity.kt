package com.skithub.resultdear.ui.GridLayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityWinningNumbersBinding

class Winning_NumbersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWinningNumbersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinningNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle("বিজয় সংখ্যাগুলি")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)
    }
}