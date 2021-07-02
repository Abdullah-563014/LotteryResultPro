package com.skithub.resultdear.ui.first_prize_winner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityFstPrizeWinnersBinding

class Fst_Prize_WinnersActivity : AppCompatActivity() {


    private lateinit var binding: ActivityFstPrizeWinnersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFstPrizeWinnersBinding.inflate(layoutInflater)
        setContentView(binding.root)





    }
}