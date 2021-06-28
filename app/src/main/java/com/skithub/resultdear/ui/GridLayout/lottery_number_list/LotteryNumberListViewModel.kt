package com.skithub.resultdear.ui.GridLayout.lottery_number_list

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class LotteryNumberListViewModel(val myApi: MyApi): ViewModel() {



    suspend fun getLotteryNumberListByWinType(pageNumber: String, itemCount: String,winType: String) = PdfRepositories().getLotteryNumberListByWinType(pageNumber,itemCount,winType,myApi)

    suspend fun getLotteryNumberListByWinTimeAndWinType(pageNumber: String, itemCount: String, winTime: String,winType: String) = PdfRepositories().getLotteryNumberListByWinTimeAndWinType(pageNumber,itemCount,winTime,winType,myApi)







}