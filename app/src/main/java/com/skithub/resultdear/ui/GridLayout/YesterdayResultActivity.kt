package com.skithub.resultdear.ui.GridLayout

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityYesterdayResultBinding
import com.skithub.resultdear.ui.pdf_info.PdfInfoActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants

class YesterdayResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityYesterdayResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYesterdayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle("গতকালের ফলাফল")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24)



        initAll()

        updateUi()


    }


    private fun initAll() {
        binding.cardView.setOnClickListener(this)
        binding.cardView2.setOnClickListener(this)
        binding.cardView3.setOnClickListener(this)
    }

    private fun updateUi() {
        binding.fastTextView.text="Yesterday Result ${CommonMethod.increaseDecreaseDaysUsingValue(-1)} ${CommonMethod.getDayNameUsingDate(CommonMethod.increaseDecreaseDaysUsingValue(-1))}"
    }

    override fun onClick(v: View?) {
        v?.let {
            var pdfInfoIntent: Intent
            when (it.id) {
                R.id.cardView -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.morningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView2 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.eveningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView3 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey, CommonMethod.increaseDecreaseDaysUsingValue(-1))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey, Constants.nightTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }
            }
        }
    }



}