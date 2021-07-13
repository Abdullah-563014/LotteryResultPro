package com.skithub.resultdear.ui.yes_vs_pre

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityYesVsPreBinding
import com.skithub.resultdear.ui.result_image_info.ResultImageInfoActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import java.util.*

class YesVsPreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityYesVsPreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYesVsPreBinding.inflate(layoutInflater)
        setContentView(binding.root)





        initAll()



    }


    private fun initAll() {
        binding.morningButton.setOnClickListener(this)
        binding.eveningButton.setOnClickListener(this)
        binding.nightButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            var pdfInfoIntent: Intent
            when (it.id) {
                R.id.morningButton -> {
                    pdfInfoIntent= Intent(this, ResultImageInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.morningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
                    startActivity(pdfInfoIntent)
                }

                R.id.eveningButton -> {
                    pdfInfoIntent= Intent(this, ResultImageInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.eveningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
                    startActivity(pdfInfoIntent)
                }

                R.id.nightButton -> {
                    pdfInfoIntent= Intent(this, ResultImageInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1, Locale.ENGLISH))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.nightTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
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