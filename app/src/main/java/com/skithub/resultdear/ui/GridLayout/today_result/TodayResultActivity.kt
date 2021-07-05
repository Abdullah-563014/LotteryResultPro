package com.skithub.resultdear.ui.GridLayout.today_result

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityTodayResultBinding
import com.skithub.resultdear.ui.pdf_info.PdfInfoActivity
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants

class TodayResultActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityTodayResultBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodayResultBinding.inflate(layoutInflater)
        setContentView(binding.root)




        initAll()






    }


    private fun initAll() {
        binding.cardView.setOnClickListener(this)
        binding.cardView1.setOnClickListener(this)
        binding.cardView2.setOnClickListener(this)
        binding.cardView3.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        v?.let {
            var pdfInfoIntent: Intent
            when (it.id) {
                R.id.cardView -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey,Constants.morningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView1 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey,Constants.eveningTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView2 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey,Constants.nightTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }

                R.id.cardView3 -> {
                    pdfInfoIntent= Intent(this, PdfInfoActivity::class.java)
                    pdfInfoIntent.putExtra(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0))
                    pdfInfoIntent.putExtra(Constants.resultTimeKey,Constants.bumperTime)
                    pdfInfoIntent.putExtra(Constants.isVersusResultKey,false)
                    startActivity(pdfInfoIntent)
                }
            }
        }
    }


}