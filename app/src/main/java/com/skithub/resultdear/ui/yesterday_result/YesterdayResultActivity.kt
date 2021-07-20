package com.skithub.resultdear.ui.yesterday_result

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityYesterdayResultBinding
import com.skithub.resultdear.ui.lottery_result_info.LotteryResultInfoActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import java.util.*

class YesterdayResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityYesterdayResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYesterdayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)




        initAll()

        updateUi()


    }


    private fun initAll() {
        binding.morningButton.setOnClickListener(this)
        binding.eveningButton.setOnClickListener(this)
        binding.nightButton.setOnClickListener(this)
    }

    private fun updateUi() {
        binding.yesterdayTimeTextView.text="Yesterday Result ${CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.getDefault())} ${CommonMethod.getDayNameUsingDate(CommonMethod.increaseDecreaseDaysUsingValue(-1,Locale.getDefault()),Locale.getDefault())}"
    }

    override fun onClick(v: View?) {
        v?.let {
            var pdfInfoIntent: Intent
            when (it.id) {
                R.id.morningButton -> {
                    pdfInfoIntent= Intent(this, LotteryResultInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.noonTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.eveningButton -> {
                    pdfInfoIntent= Intent(this, LotteryResultInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.eveningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.nightButton -> {
                    pdfInfoIntent= Intent(this, LotteryResultInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.nightTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }



}