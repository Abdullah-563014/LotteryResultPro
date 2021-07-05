package com.skithub.resultdear.ui.GridLayout.claim_form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skithub.resultdear.database.network.MyApi

class ClaimFormViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClaimFormViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClaimFormViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}