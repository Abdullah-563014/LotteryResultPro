package com.skithub.resultdear.ui.pdf_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.database.network.MyApi

class PdfInfoViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PdfInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PdfInfoViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}