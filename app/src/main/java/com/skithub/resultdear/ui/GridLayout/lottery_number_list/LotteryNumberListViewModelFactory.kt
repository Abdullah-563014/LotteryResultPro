package com.skithub.resultdear.ui.GridLayout.lottery_number_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.database.network.MyApi

class LotteryNumberListViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LotteryNumberListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LotteryNumberListViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}