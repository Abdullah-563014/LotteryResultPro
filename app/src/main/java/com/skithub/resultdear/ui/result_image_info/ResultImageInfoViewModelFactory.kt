package com.skithub.resultdear.ui.result_image_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.database.network.MyApi

class ResultImageInfoViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultImageInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ResultImageInfoViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}