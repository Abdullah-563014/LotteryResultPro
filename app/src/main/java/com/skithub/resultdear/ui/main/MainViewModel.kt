package com.skithub.resultdear.ui.main

import androidx.lifecycle.ViewModel
import com.skithub.resultdear.database.PdfRepositories
import com.skithub.resultdear.database.network.MyApi

class MainViewModel(var myApi: MyApi): ViewModel() {




    suspend fun getHomeTutorialInfo() = PdfRepositories().getHomeTutorialInfo(myApi)









}