package com.skithub.resultdear.ui.lottery_result_info

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class LotteryResultInfoViewModel(var myApi: MyApi): ViewModel() {




    suspend fun getLotteryNumberListByDateTime(date: String, time: String) =PdfRepositories().getLotteryNumberListByDateTime(date,time,myApi)









}