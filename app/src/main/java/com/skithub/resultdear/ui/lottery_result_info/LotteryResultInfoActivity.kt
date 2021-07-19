package com.skithub.resultdear.ui.lottery_result_info

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skithub.resultdear.R
import com.skithub.resultdear.adapter.LotteryResultRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityLotteryResultInfoBinding
import com.skithub.resultdear.model.AdsImageModel
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryResultRecyclerModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast
import java.util.*


class LotteryResultInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLotteryResultInfoBinding
    private lateinit var viewModelLottery: LotteryResultInfoViewModel
    private var resultDate: String=CommonMethod.increaseDecreaseDaysUsingValue(0, Locale.ENGLISH)
    private var resultTime: String=Constants.eveningTime
    private var resultDateTwo: String=CommonMethod.increaseDecreaseDaysUsingValue(-2, Locale.ENGLISH)
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private var finalList: MutableList<LotteryResultRecyclerModel> = arrayListOf()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: LotteryResultRecyclerAdapter
    private var adsImageList: MutableList<AdsImageModel> = arrayListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLotteryResultInfoBinding.inflate(layoutInflater)
        val factoryLottery: LotteryResultInfoViewModelFactory= LotteryResultInfoViewModelFactory((application as MyApplication).myApi)
        viewModelLottery=ViewModelProvider(this,factoryLottery).get(LotteryResultInfoViewModel::class.java)
        setContentView(binding.root)


        val bundle=intent.extras
        if (bundle!=null) {
            resultDateTwo=bundle.getString(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0, Locale.ENGLISH))
            resultDate=bundle.getString(Constants.resultDateKey,CommonMethod.increaseDecreaseDaysUsingValue(0, Locale.ENGLISH))
            resultTime=bundle.getString(Constants.resultTimeKey,Constants.morningTime)
        }

        initAll()

        setUpRecyclerView()

        loadLotteryNumberInfoUsingDateAndTime()

        loadAdsImageInfo()






    }


    private fun initAll() {
        binding.imageViewSwipeRefreshLayout.setOnRefreshListener {
            binding.imageViewSwipeRefreshLayout.isRefreshing=false
            loadLotteryNumberInfoUsingDateAndTime()
        }
        binding.leftDateTextView.text=resultDate
        binding.leftTimeTextView.text=resultTime
        binding.rightDateTextView.text=resultDate
        binding.rightTimeTextView.text=resultTime
        binding.stateNameTextView.text=resources.getString(R.string.nagaland_state)
    }

    private fun setUpRecyclerView() {
        layoutManager= LinearLayoutManager(this)
        adapter= LotteryResultRecyclerAdapter(this,finalList,adsImageList)
        binding.resultRecyclerView.layoutManager=layoutManager
        binding.resultRecyclerView.adapter=adapter
    }

    private fun loadAdsImageInfo() {
        Coroutines.main {
            try {
                adsImageList.clear()
                val response=viewModelLottery.getAdsImageInfo()
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success")) {
                            try {
                                adsImageList.addAll(response.body()?.data!!)
                            } finally {
                                adapter.notifyDataSetChanged()
                            }
                        }
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    private fun loadLotteryNumberInfoUsingDateAndTime() {
        Coroutines.main {
            try {
                binding.spinKit.visibility=View.VISIBLE
                binding.resultRootLayout.visibility=View.GONE
                binding.waitingRootLayout.visibility=View.GONE
                val response=viewModelLottery.getLotteryNumberListByDateTime(resultDate,resultTime)
                binding.spinKit.visibility=View.GONE
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success")) {
                            list.clear()
                            list.addAll(response.body()?.data!!)
                            if (list.size>0) {
                                filteringLotteryNumber(list)
                                binding.resultRootLayout.visibility=View.VISIBLE
                                binding.waitingRootLayout.visibility=View.GONE
                            } else {
                                binding.resultRootLayout.visibility=View.GONE
                                binding.waitingRootLayout.visibility=View.VISIBLE
                            }
                        } else {
                            binding.spinKit.visibility=View.GONE
                            binding.resultRootLayout.visibility=View.GONE
                            binding.waitingRootLayout.visibility=View.VISIBLE
                            shortToast("${response.body()?.message}")
                        }
                    } else {
                        binding.spinKit.visibility=View.GONE
                        binding.resultRootLayout.visibility=View.GONE
                        binding.waitingRootLayout.visibility=View.VISIBLE
                        shortToast("Sorry, Unknown error occurred.")
                    }
                } else {
                    binding.spinKit.visibility=View.GONE
                    binding.resultRootLayout.visibility=View.GONE
                    binding.waitingRootLayout.visibility=View.VISIBLE
                    shortToast("failed for:- ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                binding.resultRootLayout.visibility=View.GONE
                binding.waitingRootLayout.visibility=View.VISIBLE
            }
        }
    }

    private fun filteringLotteryNumber(list: MutableList<LotteryNumberModel>) {
//        val firstList: MutableList<LotteryNumberModel> = arrayListOf()
        val secondList: MutableList<LotteryNumberModel> = arrayListOf()
        val thirdList: MutableList<LotteryNumberModel> = arrayListOf()
        val fourthList: MutableList<LotteryNumberModel> = arrayListOf()
        val fifthList: MutableList<LotteryNumberModel> = arrayListOf()

        for ( item in list) {
            if (item.winType.equals(Constants.winTypeFirst)) {
//                firstList.add(item)
                binding.firstPrizeLotteryNumberTextView.text="${item.lotterySerialNumber} ${item.lotteryNumber}"
                binding.remainingAllSerialTextView.text="\u20B9 1000/- ${item.lotteryNumber} (REMAINING ALL SERIALS)"
            } else if (item.winType.equals(Constants.winTypeSecond)) {
                secondList.add(item)
            } else if (item.winType.equals(Constants.winTypeThird)) {
                thirdList.add(item)
            } else if (item.winType.equals(Constants.winTypeFourth)) {
                fourthList.add(item)
            } else if (item.winType.equals(Constants.winTypeFifth)) {
                fifthList.add(item)
            }
        }
//        finalList.add(LotteryResultRecyclerModel(Constants.winTypeFirst,firstList))
        finalList.add(LotteryResultRecyclerModel(Constants.winTypeSecond,secondList))
        finalList.add(LotteryResultRecyclerModel(Constants.winTypeThird,thirdList))
        finalList.add(LotteryResultRecyclerModel(Constants.winTypeFourth,fourthList))
        finalList.add(LotteryResultRecyclerModel(Constants.winTypeFifth,fifthList))

        adapter.notifyDataSetChanged()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }




}