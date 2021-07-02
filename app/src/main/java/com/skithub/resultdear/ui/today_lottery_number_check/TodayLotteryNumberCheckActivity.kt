package com.skithub.resultdear.ui.today_lottery_number_check

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.ActivityNumberCheckBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.ui.pdf_info.PdfInfoViewModel
import com.skithub.resultdear.ui.pdf_info.PdfInfoViewModelFactory
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast

class TodayLotteryNumberCheckActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityNumberCheckBinding
    private lateinit var viewModel: TodayLotteryNumberCheckViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityNumberCheckBinding.inflate(layoutInflater)
        val factory: TodayLotteryNumberCheckViewModelFactory = TodayLotteryNumberCheckViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(TodayLotteryNumberCheckViewModel::class.java)
        setContentView(binding.root)




        initAll()



    }

    private fun initAll() {
        binding.spinKit.visibility= View.GONE
        binding.lotteryNumberCheckButton.setOnClickListener(this)
    }

    private fun checkNumber() {
        Coroutines.main {
            val lotteryNumber: String=binding.lotteryNumberEditText.text.toString()
            binding.spinKit.visibility= View.VISIBLE
            val response=viewModel.findLotteryInfoUsingLotteryNumber(lotteryNumber)
            if (response.isSuccessful && response.code()==200) {
                binding.spinKit.visibility= View.GONE
                if (response.body()?.status.equals("success",true)) {
                    val lotteryNumberModel: LotteryNumberModel=response.body()?.data!![0]
                    shortToast("win type is:- ${lotteryNumberModel.winType}")
                } else {
                    shortToast(response.body()?.message!!)
                }
            } else {
                binding.spinKit.visibility= View.GONE
                shortToast("failed for:- ${response.errorBody()?.toString()}")
            }
        }
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.lotteryNumberCheckButton -> checkNumber()
            }
        }
    }


}