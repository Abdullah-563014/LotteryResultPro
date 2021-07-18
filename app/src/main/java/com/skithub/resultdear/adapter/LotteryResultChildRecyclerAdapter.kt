package com.skithub.resultdear.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skithub.resultdear.R
import com.skithub.resultdear.databinding.LotteryResultChildRecyclerViewModelLayoutBinding
import com.skithub.resultdear.databinding.LotteryResultRecyclerViewModelLayoutBinding
import com.skithub.resultdear.model.LotteryNumberModel
import com.skithub.resultdear.utils.Constants
import com.skithub.resultdear.utils.MyExtensions.shortToast

class LotteryResultChildRecyclerAdapter(val context: Context, val list: MutableList<LotteryNumberModel>): RecyclerView.Adapter<LotteryResultChildRecyclerAdapter.LotteryResultChildRecyclerViewHolder>() {

    private val firstPrizeViewType: Int=0
    private val otherPrizeViewType: Int=1
    private val clipBoardManager: ClipboardManager=context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager


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

    override fun getItemViewType(position: Int): Int {
        return if (list[position].winType.equals(Constants.winTypeFirst)) {
            firstPrizeViewType
        } else {
            otherPrizeViewType
        }
    }

    inner class LotteryResultChildRecyclerViewHolder(val binding: LotteryResultChildRecyclerViewModelLayoutBinding): RecyclerView.ViewHolder(binding.root),
        View.OnClickListener {

        fun bind(item: LotteryNumberModel) {
            try {
                if (item.winType.equals(Constants.winTypeFirst)) {
                    binding.lotteryNumberTextView.text="${item.lotterySerialNumber} ${item.lotteryNumber}"
                    binding.lotteryNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20.0f)
                } else {
                    binding.lotteryNumberTextView.text="${item.lotteryNumber}"
                }
                binding.lotteryNumberRecyclerRootLayout.setOnClickListener(this)
            } catch (e: Exception) {

            }
        }

        override fun onClick(v: View?) {
            v?.let {
                when (it.id) {
                    R.id.lotteryNumberRecyclerRootLayout -> {
                        val number: String?=list[adapterPosition].lotteryNumber
                        val clipData=ClipData.newPlainText(Constants.TAG,"$number")
                        clipBoardManager.setPrimaryClip(clipData)
                        context.shortToast("$number ${context.resources.getString(R.string.copied_successfully)}")
                    }
                }
            }
        }
    }


}