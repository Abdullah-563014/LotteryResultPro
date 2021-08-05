package com.skithub.resultdear.ui.middle_number

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class MiddleNumberViewModel(var myApi: MyApi): ViewModel() {




    suspend fun getDuplicateLotteryNumberList(pageNumber: String, itemCount: String) =
        PdfRepositories().getDuplicateLotteryNumberList(pageNumber,itemCount,myApi)









}