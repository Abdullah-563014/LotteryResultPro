package com.skithub.resultdear.database

import com.skithub.resultdear.database.network.MyApi
import com.skithub.resultdear.model.AdsImageResponse
import com.skithub.resultdear.model.LotteryNumberResponse
import com.skithub.resultdear.model.LotteryPdfResponse
import retrofit2.Response

class PdfRepositories {


    suspend fun findSimilarLotteryNumberList(lotteryNumber: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.findSimilarLotteryNumberList(lotteryNumber)
    }

    suspend fun getLotteryNumberListUsingLotteryNumber(lotteryNumber: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListUsingLotteryNumber(lotteryNumber)
    }

    suspend fun getLotteryNumberListByWinType(pageNumber: String, itemCount: String, winType: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListByWinType(pageNumber,itemCount,winType)
    }

    suspend fun getDuplicateLotteryNumberList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getDuplicateLotteryNumberList(pageNumber,itemCount)
    }

    suspend fun getLotteryNumberListByDateTime(date: String, time: String,myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryNumberListByDateTime(date,time)
    }

    suspend fun getLotteryResultList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryNumberResponse> {
        return myApi.getLotteryResultList(pageNumber,itemCount)
    }

    suspend fun getBumperLotteryResultList(pageNumber: String, itemCount: String, myApi: MyApi): Response<LotteryPdfResponse> {
        return myApi.getBumperLotteryResultList(pageNumber,itemCount)
    }

    suspend fun getAdsImageInfo(myApi: MyApi): Response<AdsImageResponse> {
        return myApi.getAdsInfo()
    }

    suspend fun getHomeTutorialInfo(myApi: MyApi): Response<AdsImageResponse> {
        return myApi.getHomeTutorialInfo()
    }












}