package com.skithub.resultdear.ui.result_image_info

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.leinardi.android.speeddial.SpeedDialView
import com.skithub.resultdear.R
import com.skithub.resultdear.adapter.LotteryResultRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityResultImageInfoBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryPdfModel
import com.skithub.resultdear.model.LotteryResultRecyclerModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast
import java.util.*


class ResultImageInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultImageInfoBinding
    private lateinit var viewModel: ResultImageInfoViewModel
    private var resultDate: String=CommonMethod.increaseDecreaseDaysUsingValue(0, Locale.ENGLISH)
    private var resultTime: String=Constants.eveningTime
    private var resultDateTwo: String=CommonMethod.increaseDecreaseDaysUsingValue(-2, Locale.ENGLISH)
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private var finalList: MutableList<LotteryResultRecyclerModel> = arrayListOf()
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: LotteryResultRecyclerAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultImageInfoBinding.inflate(layoutInflater)
        val factory: ResultImageInfoViewModelFactory= ResultImageInfoViewModelFactory((application as MyApplication).myApi)
        viewModel=ViewModelProvider(this,factory).get(ResultImageInfoViewModel::class.java)
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






    }


    private fun initAll() {
        binding.imageViewSwipeRefreshLayout.setOnRefreshListener {
            binding.imageViewSwipeRefreshLayout.isRefreshing=false
            loadLotteryNumberInfoUsingDateAndTime()
        }
        binding.dateTextView.text=resultDate
        binding.timeTextView.text=resultTime
        binding.stateNameTextView.text=resources.getString(R.string.nagaland_state)
    }

    private fun setUpRecyclerView() {
        layoutManager= LinearLayoutManager(this)
        adapter= LotteryResultRecyclerAdapter(this,finalList)
        binding.resultRecyclerView.layoutManager=layoutManager
        binding.resultRecyclerView.adapter=adapter
    }

    private fun loadLotteryNumberInfoUsingDateAndTime() {
        Coroutines.main {
            binding.spinKit.visibility=View.VISIBLE
            binding.resultRootLayout.visibility=View.GONE
            binding.waitingRootLayout.visibility=View.GONE

            val response=viewModel.getLotteryNumberListByDateTime(resultDate,resultTime)
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
        }
    }

    private fun filteringLotteryNumber(list: MutableList<LotteryNumberModel>) {
        val firstList: MutableList<LotteryNumberModel> = arrayListOf()
        val secondList: MutableList<LotteryNumberModel> = arrayListOf()
        val thirdList: MutableList<LotteryNumberModel> = arrayListOf()
        val fourthList: MutableList<LotteryNumberModel> = arrayListOf()
        val fifthList: MutableList<LotteryNumberModel> = arrayListOf()

        for ( item in list) {
            if (item.winType.equals(Constants.winTypeFirst)) {
                firstList.add(item)
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
        finalList.add(LotteryResultRecyclerModel(Constants.winTypeFirst,firstList))
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