package com.skithub.resultdear.database

import com.skithub.resultdear.database.network.MyApi
import com.skithub.resultdear.model.AdsImageResponse
import com.skithub.resultdear.model.LotteryNumberResponse
import com.skithub.resultdear.model.LotteryPdfResponse
import retrofit2.Response

class PdfRepositories {


    suspend fun findLotteryInfoUsingLotteryNumber(lotteryNumber: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.findLotteryInfoUsingLotteryNumber(lotteryNumber)
    }

    suspend fun getLotteryNumberListUsingLotteryNumber(lotteryNumber: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListUsingLotteryNumber(lotteryNumber)
    }

    suspend fun checkTodayResultUsingNumberDateTimeAndType(lotteryNumber: String,winDate: String, winTime: String, winType: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.checkTodayResultUsingNumberDateTimeAndType(lotteryNumber,winDate,winTime,winType)
    }

    suspend fun getLotteryNumberListByWinTimeAndWinType(pageNumber: String, itemCount: String, winTime: String, winType: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListByWinTimeAndWinType(pageNumber,itemCount,winTime,winType)
    }

    suspend fun getLotteryNumberListByWinType(pageNumber: String, itemCount: String, winType: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListByWinType(pageNumber,itemCount,winType)
    }

    suspend fun getDuplicateLotteryNumberList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getDuplicateLotteryNumberList(pageNumber,itemCount)
    }

    suspend fun getLotteryInfoByDateAndTime(date: String, time: String, dateTwo: String,myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getLotteryInfoByDateAndTime(date,dateTwo,time)
    }

    suspend fun getLotteryNumberListByDateTime(date: String, time: String,myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListByDateTime(date,time)
    }

    suspend fun getLotteryResultList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getLotteryResultList(pageNumber,itemCount)
    }

    suspend fun getBumperLotteryInfoByDateAndTime(date: String, time: String, dateTwo: String,myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getBumperLotteryInfoByDateAndTime(date,dateTwo,time)
    }

    suspend fun getBumperLotteryResultList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getBumperLotteryResultList(pageNumber,itemCount)
    }

    suspend fun getAdsImageInfo(myApi: MyApi): Response<AdsImageResponse> {
        return myApi.getAdsInfo()
    }












}