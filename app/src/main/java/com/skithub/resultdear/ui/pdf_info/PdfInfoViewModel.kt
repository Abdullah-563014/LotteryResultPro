package com.skithub.resultdear.ui.pdf_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi
import com.skithub.resultdear.utils.Coroutines
import kotlinx.coroutines.Dispatchers.IO

class PdfInfoViewModel(var myApi: MyApi): ViewModel() {




    suspend fun lotteryInfoByDateAndTime(date: String, time: String, dateTwo: String) =PdfRepositories().getLotteryInfoByDateAndTime(date,time,dateTwo,myApi)









}