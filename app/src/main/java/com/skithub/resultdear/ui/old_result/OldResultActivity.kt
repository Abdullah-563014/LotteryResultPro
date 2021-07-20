package com.skithub.resultdear.ui.old_result

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.adapter.OldResultRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityOldResultBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryPdfModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Coroutines

class OldResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOldResultBinding
    private lateinit var viewModel: OldResultViewModel
    private lateinit var adapter: OldResultRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private var page_number: Int=1
    private var item_count: Int=30
//    private var past_visible_item: Int =0
//    private var visible_item_count: Int =0
//    private var total_item_count: Int =0
//    private var previous_total: Int =0
//    private var isLoading: Boolean=true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOldResultBinding.inflate(layoutInflater)
        val factory: OldResultViewModelFactory = OldResultViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(OldResultViewModel::class.java)
        setContentView(binding.root)





        initAll()

        setupRecyclerView()

        loadAllLotteryResult()



    }


    private fun initAll() {
        binding.spinKit.visibility=View.GONE
    }

    private fun setupRecyclerView() {
        adapter= OldResultRecyclerAdapter(this,list)
        layoutManager= LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=adapter
//        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                visible_item_count=layoutManager.childCount
//                total_item_count=layoutManager.itemCount
//                past_visible_item=layoutManager.findFirstVisibleItemPosition()
//                if (dy>0) {
//                    if (isLoading) {
//                        if (total_item_count > previous_total) {
//                            isLoading = false
//                            previous_total = total_item_count
//                        }
//                        if (!isLoading && (total_item_count - visible_item_count) <= (past_visible_item + item_count)) {
//                            page_number++
//                            loadAllLotteryResult()
//                            isLoading = true
//                        }
//                    }
//                }
//            }
//        })
    }

    private fun loadAllLotteryResult() {
        Coroutines.main {
            try {
                binding.spinKit.visibility=View.VISIBLE
                val response=viewModel.lotteryResultList(page_number.toString(),item_count.toString())
                binding.spinKit.visibility=View.GONE
                if (response.isSuccessful && response.code()==200) {
                    if (response.body()!=null) {
                        if (response.body()!!.status.equals("success",true)) {
                            list.clear()
                            list.addAll(response.body()!!.data!!)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
            } catch (e: Exception) {
                binding.spinKit.visibility=View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.particleView.pause()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }









}