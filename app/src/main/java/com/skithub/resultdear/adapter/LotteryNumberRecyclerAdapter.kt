package com.skithub.resultdear.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.LotteryNumberRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.utils.Constants

class LotteryNumberRecyclerAdapter(val context: Context, val list: MutableList<LotteryNumberModel>): RecyclerView.Adapter<LotteryNumberRecyclerAdapter.LotteryNumberRecyclerViewHolder>() {

    private val firstPrizeResultType:Int=0
    private val otherPrizeResultType:Int=1



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotteryNumberRecyclerViewHolder {
        val binding=LotteryNumberRecyclerViewModelLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LotteryNumberRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LotteryNumberRecyclerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].winType!!.equals(Constants.winTypeFirst)) {
            firstPrizeResultType
        } else {
            otherPrizeResultType
        }
    }


    inner class LotteryNumberRecyclerViewHolder(val binding: LotteryNumberRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LotteryNumberModel) {
            if (item.winType!!.equals(Constants.winTypeFirst)) {
                binding.lotteryNumberTextView.text="${item.lotterySerialNumber} ${item.lotterySerialNumber}"
                binding.lotteryMessageTextView.text="${item.message}"
                binding.lotteryMessageTextView.visibility=View.VISIBLE
            } else {
                binding.lotteryNumberTextView.text="${item.lotterySerialNumber}"
                binding.lotteryMessageTextView.visibility=View.GONE
            }
        }
    }




}