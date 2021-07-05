package com.skithub.resultdear.ui.GridLayout.claim_form

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class ClaimFormViewModel(var myApi: MyApi): ViewModel() {


    suspend fun lotteryResultList(pageNumber: String, itemCount: String) = PdfRepositories().getLotteryResultList(pageNumber,itemCount,myApi)








}