package com.skithub.resultdear.ui.lucky_number

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityYourLuckyNumbersBinding

class Your_Lucky_NumbersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityYourLuckyNumbersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourLuckyNumbersBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)






    }









}