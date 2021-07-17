package com.skithub.resultdear.database.network

import android.util.Base64
import com.google.gson.JsonElement
import com.skithub.resultdear.BuildConfig
import com.skithub.resultdear.model.LotteryNumberResponse
import com.skithub.resultdear.model.LotteryPdfResponse
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {


    @GET("find_lottery_number_info.php?")
    suspend fun findLotteryInfoUsingLotteryNumber(
        @Query("LotteryNumber") lotteryNumber: String
    ): Response<LotteryNumberResponse>

    @GET("get_lottery_number_list_by_lottery_number.php?")
    suspend fun getLotteryNumberListUsingLotteryNumber(
        @Query("LotteryNumber") lotteryNumber: String
    ): Response<LotteryNumberResponse>

    @GET("get_lottery_number_list_by_win_date_time.php?")
    suspend fun getLotteryNumberListByDateTime(
        @Query("WinDate") winDate: String,
        @Query("WinTime") winTime: String
    ): Response<LotteryNumberResponse>

    @GET("check_today_result_by_lottery_number_and_win_date_time_type.php?")
    suspend fun checkTodayResultUsingNumberDateTimeAndType(
        @Query("LotteryNumber") lotteryNumber: String,
        @Query("WinDate") winDate: String,
        @Query("WinTime") winTime: String,
        @Query("WinType") winType: String
    ): Response<LotteryNumberResponse>

    @GET("get_lottery_number_list_by_time_and_win_type.php?")
    suspend fun getLotteryNumberListByWinTimeAndWinType(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String,
        @Query("WinTime") winTime: String,
        @Query("WinType") winType: String
    ): Response<LotteryNumberResponse>

    @GET("get_lottery_number_list_by_win_type.php?")
    suspend fun getLotteryNumberListByWinType(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String,
        @Query("WinType") winType: String
    ): Response<LotteryNumberResponse>

    @GET("get_duplicate_lottery_number_list.php?")
    suspend fun getDuplicateLotteryNumberList(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String
    ): Response<LotteryNumberResponse>

    @GET("index.php?")
    suspend fun getLotteryResultList(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String
    ): Response<LotteryPdfResponse>

    @GET("get_bumper_result_list.php?")
    suspend fun getBumperLotteryResultList(
        @Query("PageNumber") pageNumber: String,
        @Query("ItemCount") itemCount: String
    ): Response<LotteryPdfResponse>

    @GET("get_lottery_result_info_by_date_and_time.php?")
    suspend fun getLotteryInfoByDateAndTime(
        @Query("ResultDate") resultDate: String,
        @Query("ResultDateTwo") resultDateTwo: String,
        @Query("ResultTime") resultTime: String
    ): Response<LotteryPdfResponse>

    @GET("get_bumper_lottery_result_info_by_date_and_time.php?")
    suspend fun getBumperLotteryInfoByDateAndTime(
        @Query("ResultDate") resultDate: String,
        @Query("ResultDateTwo") resultDateTwo: String,
        @Query("ResultTime") resultTime: String
    ): Response<LotteryPdfResponse>


    companion object {

        @Volatile
        private var myApiInstance: MyApi? = null
        private val LOCK = Any()

        operator fun invoke() = myApiInstance ?: synchronized(LOCK) {
            myApiInstance ?: createClient().also {
                myApiInstance = it
            }
        }

        private fun createClient(): MyApi {
            val AUTH: String = "Basic ${
                Base64.encodeToString(
                    ("${BuildConfig.USER_NAME}:${BuildConfig.USER_PASSWORD}").toByteArray(),
                    Base64.NO_WRAP
                )
            }"
            val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder = original.newBuilder()
                        .addHeader("Authorization", AUTH)
                        .method(original.method(), original.body())
                    val request: Request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MyApi::class.java)
        }


    }


}