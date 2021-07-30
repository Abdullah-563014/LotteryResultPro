package com.skithub.resultdear.ui.main

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class MainViewModel(var myApi: MyApi): ViewModel() {




    suspend fun getHomeTutorialInfo() = PdfRepositories().getHomeTutorialInfo(myApi)

    suspend fun uploadUserInfo(token: String, phone: String,registrationDate: String,activeStatus: String) = PdfRepositories().uploadUserInfo(token,phone,registrationDate,activeStatus,myApi)

    suspend fun getUserInfoByToken(token: String) = PdfRepositories().getUserInfoByToken(token,myApi)









}