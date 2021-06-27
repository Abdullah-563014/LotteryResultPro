package com.skithub.resultdear.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.skithub.resultdear.database.network.MyApi
import com.skithub.resultdear.model.LotteryPdfResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PdfRepositories {


    suspend fun getLotteryInfoByDateAndTime(date: String, time: String, dateTwo: String,myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getLotteryInfoByDateAndTime(date,dateTwo,time)
    }

    suspend fun getLotteryResultList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getLotteryResultList(pageNumber,itemCount)
    }








}