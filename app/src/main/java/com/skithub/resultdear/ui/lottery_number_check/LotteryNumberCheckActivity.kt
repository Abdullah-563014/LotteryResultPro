package com.skithub.resultdear.ui.lottery_number_check

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.skithub.resultdear.R
import com.skithub.resultdear.adapter.LotteryNumberRecyclerAdapter
import com.skithub.resultdear.databinding.ActivityLotteryNumberCheckBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.ui.MyApplication
import com.skithub.resultdear.utils.CommonMethod
import com.skithub.resultdear.utils.Coroutines
import com.skithub.resultdear.utils.MyExtensions.shortToast

class LotteryNumberCheckActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLotteryNumberCheckBinding
    private lateinit var viewModel: LotteryNumberCheckViewModel
    private var list: MutableList<LotteryNumberModel> = arrayListOf()
    private lateinit var adapter: LotteryNumberRecyclerAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var inputMethodManager: InputMethodManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLotteryNumberCheckBinding.inflate(layoutInflater)
        val factory: LotteryNumberCheckViewModelFactory = LotteryNumberCheckViewModelFactory((application as MyApplication).myApi)
        viewModel= ViewModelProvider(this,factory).get(LotteryNumberCheckViewModel::class.java)
        setContentView(binding.root)




        initAll()

        setUpRecyclerView()



    }

    private fun initAll() {
        inputMethodManager=getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        binding.spinKit.visibility= View.GONE
        binding.recyclerView.visibility= View.GONE
        binding.lotteryNumberCheckButton.setOnClickListener(this)
    }

    private fun setUpRecyclerView() {
        layoutManager= LinearLayoutManager(this)
        adapter= LotteryNumberRecyclerAdapter(this,list)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.adapter=adapter
    }

    private fun checkNumber() {
        Coroutines.main {
            try {
                hideKeyBoard()
                val lotteryNumber: String=binding.lotteryNumberEditText.text.toString()
                binding.spinKit.visibility= View.VISIBLE
                list.clear()
                adapter.notifyDataSetChanged()
                val response=viewModel.findLotteryInfoUsingLotteryNumber(lotteryNumber)
                if (response.isSuccessful && response.code()==200) {
                    binding.spinKit.visibility= View.GONE
                    if (response.body()?.status.equals("success",true)) {
                        list.addAll(response.body()?.data!!)
                        adapter.notifyDataSetChanged()
                        if (list.size>0) {
                            binding.recyclerView.visibility=View.VISIBLE
                        } else {
                            binding.recyclerView.visibility=View.GONE
                            shortToast("Sorry, no data found")
                        }
                    } else {
                        shortToast(response.body()?.message!!)
                    }
                } else {
                    binding.spinKit.visibility= View.GONE
                    shortToast("failed for:- ${response.errorBody()?.toString()}")
                }
            } catch (e: Exception) {
                binding.spinKit.visibility= View.GONE
            }
        }
    }

    private fun hideKeyBoard() {
        inputMethodManager.hideSoftInputFromWindow(binding.lotteryNumberEditText.windowToken,0)
    }

    override fun onClick(v: View?) {
        v?.let {
            when (it.id) {
                R.id.lotteryNumberCheckButton -> checkNumber()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        binding.particleView.resume()
    }

    override fun onPause() {
        super.onPause()
//        binding.particleView.pause()
    }

    override fun attachBaseContext(newBase: Context?) {
        if (newBase!=null) {
            super.attachBaseContext(CommonMethod.updateLanguage(newBase))
        } else {
            super.attachBaseContext(newBase)
        }
    }


}