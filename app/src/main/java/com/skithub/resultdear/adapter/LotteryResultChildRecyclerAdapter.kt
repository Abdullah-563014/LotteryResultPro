package com.skithub.resultdear.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.databinding.LotteryResultChildRecyclerViewModelLayoutBinding
import com.skithub.resultdear.databinding.LotteryResultRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.utils.Constants

class LotteryResultChildRecyclerAdapter(val context: Context, val list: MutableList<LotteryNumberModel>): RecyclerView.Adapter<LotteryResultChildRecyclerAdapter.LotteryResultChildRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotteryResultChildRecyclerViewHolder {
        val binding= LotteryResultChildRecyclerViewModelLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LotteryResultChildRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LotteryResultChildRecyclerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class LotteryResultChildRecyclerViewHolder(val binding: LotteryResultChildRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LotteryNumberModel) {
            try {
                if (item.winType.equals(Constants.winTypeFirst)) {
                    binding.lotteryNumberTextView.text="${item.lotterySerialNumber} ${item.lotteryNumber}"
                } else {
                    binding.lotteryNumberTextView.text="${item.lotteryNumber}"
                }
            } catch (e: Exception) {

            }
        }
    }


}