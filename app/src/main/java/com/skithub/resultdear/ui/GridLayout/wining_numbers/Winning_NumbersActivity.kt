package com.skithub.resultdear.ui.GridLayout.wining_numbers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityWinningNumbersBinding
import com.skithub.resultdear.ui.GridLayout.lottery_number_list.LotteryNumberListActivity
import com.skithub.resultdear.utils.Constants

class Winning_NumbersActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityWinningNumbersBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinningNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)




        initAll()



    }


    private fun initAll() {
        binding.cardView.setOnClickListener(this)
        binding.cardView2.setOnClickListener(this)
        binding.cardView3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let{
            val winingNumberIntent: Intent= Intent(this,LotteryNumberListActivity::class.java)
            when (it.id) {
                R.id.cardView -> {
                    winingNumberIntent.putExtra(Constants.winTypeKey,Constants.winTypeFirst)
                    startActivity(winingNumberIntent)
                }

                R.id.cardView2 -> {
                    winingNumberIntent.putExtra(Constants.winTypeKey,Constants.winTypeSecond)
                    startActivity(winingNumberIntent)
                }

                R.id.cardView3 -> {
                    winingNumberIntent.putExtra(Constants.winTypeKey,Constants.winTypeThird)
                    startActivity(winingNumberIntent)
                }

            }
        }
    }


}