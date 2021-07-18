package com.skithub.resultdear.adapter

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.LotteryResultRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.model.LotteryResultRecyclerModel
import com.skithub.resultdear.utils.Constants

class LotteryResultRecyclerAdapter(val context: Context, val list: MutableList<LotteryResultRecyclerModel>): RecyclerView.Adapter<LotteryResultRecyclerAdapter.LotteryResultRecyclerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LotteryResultRecyclerViewHolder {
        val binding=LotteryResultRecyclerViewModelLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LotteryResultRecyclerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LotteryResultRecyclerViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class LotteryResultRecyclerViewHolder(val binding: LotteryResultRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LotteryResultRecyclerModel) {
            try {
                if (item.winType.equals(Constants.winTypeFirst)) {
                    binding.resultTypeTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,23.0f)
                }
                binding.resultTypeTextView.text="${context.resources.getString(R.string.win_type)}:- ${item.winType}"
                val layoutManager: GridLayoutManager= GridLayoutManager(context,7)
                val childList: MutableList<LotteryNumberModel> =item.data!!
                childList.sortBy {
                    it.lotteryNumber
                }
                val adapter: LotteryResultChildRecyclerAdapter=LotteryResultChildRecyclerAdapter(context,childList)
                layoutManager.spanSizeLookup= object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter.getItemViewType(position)==0) {
                            7
                        } else {
                            1
                        }
                    }
                }
                binding.resultChildRecyclerView.layoutManager=layoutManager
                binding.resultChildRecyclerView.adapter=adapter
            } catch (e: Exception) {

            }
        }
    }


}