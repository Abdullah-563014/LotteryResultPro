package com.skithub.resultdear.ui.GridLayout.lottery_number_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.R
import com.skithub.resultdear.adapter.LotteryNumberRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityLotteryNumberListBinding
import com.skithub.resultdear.databinding.ActivityOldResultBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryNumberResponse
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultViewModel
import com.skithub.resultdear.ui.GridLayout.old_result.OldResultViewModelFactory
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast
import retrofit2.Response

class LotteryNumberListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLotteryNumberListBinding
    private lateinit var viewModel: LotteryNumberListViewModel
    private lateinit var adapter: LotteryNumberRecyclerAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private var page_number: Int=1
    private var item_count: Int=30
    private var past_visible_item: Int =0
    private var visible_item_count: Int =0
    private var total_item_count: Int =0
    private var previous_total: Int =0
    private var isLoading: Boolean=true
    private var winType: String=Constants.winTypeFirst
    private var winTime: String=Constants.morningTime




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLotteryNumberListBinding.inflate(layoutInflater)
        val factory: LotteryNumberListViewModelFactory = LotteryNumberListViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(LotteryNumberListViewModel::class.java)
        setContentView(binding.root)



        val bundle=intent.extras
        if (bundle!=null) {
            winType=bundle.getString(Constants.winTypeKey,Constants.winTypeFirst)
            winTime=bundle.getString(Constants.resultTimeKey,Constants.morningTime)
        }



        initAll()

        setUpDropDownTextView()

        setUpRecyclerView()

        loadData()






    }


    private fun initAll() {
        binding.spinKit.visibility=View.GONE
    }

    private fun setUpDropDownTextView() {
        val dropDownList= arrayListOf<String>()
        dropDownList.add("Click to change publish time:- ${Constants.morningTime}")
        dropDownList.add("Click to change publish time:- ${Constants.eveningTime}")
        dropDownList.add("Click to change publish time:- ${Constants.nightTime}")
        binding.dropDownTextView.setOptions(dropDownList)
        binding.dropDownTextView.addTextChangedListener {
            getWinTimeValueFromDropDownText()
            page_number=1
            past_visible_item=0
            visible_item_count=0
            total_item_count=0
            previous_total=0
            isLoading=true
            list.clear()
            adapter.notifyDataSetChanged()
            loadData()
        }
    }

    private fun getWinTimeValueFromDropDownText() {
        val time: String=binding.dropDownTextView.text.toString()
        if (time.contains(Constants.morningTime)) {
            winTime=Constants.morningTime
        } else if (time.contains(Constants.eveningTime)) {
            winTime=Constants.eveningTime
        } else if (time.contains(Constants.nightTime)) {
            winTime=Constants.nightTime
        }
    }

    private fun setUpRecyclerView() {
        adapter=LotteryNumberRecyclerAdapter(this,list)
        layoutManager= GridLayoutManager(this,3)
        layoutManager.spanSizeLookup= object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter.getItemViewType(position)==0) {
                    return 3
                } else {
                    return 1
                }
            }
        }
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                visible_item_count=layoutManager.childCount
                total_item_count=layoutManager.itemCount
                past_visible_item=layoutManager.findFirstVisibleItemPosition()
                if (dy>0) {
                    if (isLoading) {
                        if (total_item_count > previous_total) {
                            isLoading = false
                            previous_total = total_item_count
                        }
                        if (!isLoading && (total_item_count - visible_item_count) <= (past_visible_item + item_count)) {
                            page_number++
                            loadData()
                            isLoading = true
                        }
                    }
                }
            }
        })
    }

    private fun loadData() {
        Coroutines.main {
            getWinTimeValueFromDropDownText()
            binding.spinKit.visibility=View.VISIBLE
            var response=viewModel.getLotteryNumberListByWinTimeAndWinType(page_number.toString(),item_count.toString(),winTime,winType)
            if (response.isSuccessful && response.code()==200 && response.body()!=null) {
                binding.spinKit.visibility=View.GONE
                if (response.body()!!.status.equals("success")) {
                    list.addAll(response.body()!!.data!!)
                    adapter.notifyDataSetChanged()
                } else {
                    shortToast(response.body()!!.message!!)
                }
            } else {
                binding.spinKit.visibility=View.GONE
                shortToast("failed for:- ${response.errorBody()?.toString()}")
            }
        }
    }











}