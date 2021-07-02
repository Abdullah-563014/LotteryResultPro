package com.skithub.resultdear.ui.today_lottery_number_check

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class TodayLotteryNumberCheckViewModel(var myApi: MyApi): ViewModel() {




    suspend fun findLotteryInfoUsingLotteryNumber(lotteryNumber: String) =
        PdfRepositories().findLotteryInfoUsingLotteryNumber(lotteryNumber,myApi)









}