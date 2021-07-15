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


    inner class LotteryNumberRecyclerViewHolder(val binding: LotteryNumberRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LotteryNumberModel) {
            binding.lotteryNumberTextView.text="${item.lotterySerialNumber} ${item.lotteryNumber}"
            binding.winDateTextView.text="Win Date:- ${item.winDate}"
            binding.winTimeTextView.text="Win Time:- ${item.winTime}"
            binding.winTypeTextView.text="Prize Type:- ${item.winType}"
            binding.prizeAmountTextView.text="Prize Amount:- ${getPrizeAmount(item.winType)}"
        }
    }

    private fun getPrizeAmount(type: String?): String {
        if (type.isNullOrEmpty()) {
            return "Amount not detectable"
        } else {
            if (type.equals(Constants.winTypeFirst)) {
                return "1 Crore"
            } else if (type.equals(Constants.winTypeSecond)) {
                return "(Rs:9,000)"
            } else if (type.equals(Constants.winTypeThird)) {
                return "(Rs:500)"
            } else if (type.equals(Constants.winTypeFourth)) {
                return "(Rs:250)"
            } else if (type.equals(Constants.winTypeFifth)) {
                return "(Rs:120)"
            }
        }
        return "Amount not detectable"
    }




}