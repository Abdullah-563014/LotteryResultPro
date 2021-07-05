package com.skithub.resultdear.ui.GridLayout.yes_vs_pre

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityYesVsPreBinding
import com.skithub.resultdear.ui.pdf_info.PdfInfoActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants

class YesVsPreActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityYesVsPreBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYesVsPreBinding.inflate(layoutInflater)
        setContentView(binding.root)





        initAll()



    }


    private fun initAll() {
        binding.cardView.setOnClickListener(this)
        binding.cardView2.setOnClickListener(this)
        binding.cardView3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            var pdfInfoIntent: Intent
            when (it.id) {
                R.id.cardView -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.morningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView2 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.eveningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView3 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.nightTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,true)
                    startActivity(pdfInfoIntent)
                }
            }
        }
    }






}