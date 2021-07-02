package com.skithub.resultdear.ui.today_lottery_number_check

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.database.network.MyApi

class TodayLotteryNumberCheckViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodayLotteryNumberCheckViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodayLotteryNumberCheckViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}