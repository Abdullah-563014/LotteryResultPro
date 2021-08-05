package com.skithub.resultdear.ui.middle_number

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skithub.resultdear.R
import com.skithub.resultdear.adapter.DuplicateLotteryNumberRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityCommonNumberBinding
import com.skithub.resultdear.databinding.ActivityMiddleNumberBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.ui.common_number.CommonNumberViewModel
import com.skithub.resultdear.ui.common_number.CommonNumberViewModelFactory
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast

class MiddleNumberActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMiddleNumberBinding
    private lateinit var viewModel: MiddleNumberViewModel
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private lateinit var adapter: DuplicateLotteryNumberRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page_number: Int=1
    private var item_count: Int=30


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMiddleNumberBinding.inflate(layoutInflater)
        val factory= MiddleNumberViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(MiddleNumberViewModel::class.java)
        setContentView(binding.root)




        initAll()

        setupRecyclerView()

        loadDuplicateLotteryNumber()



    }


    private fun initAll() {
        binding.spinKit.visibility= View.GONE
    }

    private fun setupRecyclerView() {
        adapter= DuplicateLotteryNumberRecyclerAdapter(this,list)
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
//                            loadDuplicateLotteryNumber()
//                            isLoading = true
//                        }
//                    }
//                }
//            }
//        })
    }

    private fun loadDuplicateLotteryNumber() {
        Coroutines.main {
            try {
                binding.spinKit.visibility= View.VISIBLE
                val response=viewModel.getDuplicateLotteryNumberList(page_number.toString(),item_count.toString())
                if (response.isSuccessful && response.code()==200) {
                    binding.spinKit.visibility= View.GONE
                    if (response.body()!=null) {
                        if (response.body()?.status.equals("success",true)) {
                            list.addAll(response.body()?.data!!)
                            adapter.notifyDataSetChanged()
                        } else {
                            shortToast("message:- ${response.body()?.message}")
                        }
                    }
                } else {
                    binding.spinKit.visibility= View.GONE
                }
            } catch (e: Exception) {
                binding.spinKit.visibility= View.GONE
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